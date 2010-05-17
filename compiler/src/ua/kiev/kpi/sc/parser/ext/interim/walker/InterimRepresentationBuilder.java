package ua.kiev.kpi.sc.parser.ext.interim.walker;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import ua.kiev.kpi.sc.parser.analysis.DepthFirstAdapter;
import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.interim.InvisibleTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.Translation.AbstractTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Comment;
import ua.kiev.kpi.sc.parser.ext.interim.repr.FuncPointer;
import ua.kiev.kpi.sc.parser.ext.interim.repr.JumpAlways;
import ua.kiev.kpi.sc.parser.ext.interim.repr.JumpIfFalse;
import ua.kiev.kpi.sc.parser.ext.interim.repr.LabelDeclaration;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Literal;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Marker;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Operation;
import ua.kiev.kpi.sc.parser.ext.interim.repr.TypePointer;
import ua.kiev.kpi.sc.parser.ext.interim.repr.VariablePointer;
import ua.kiev.kpi.sc.parser.node.AAddSimpleExpression;
import ua.kiev.kpi.sc.parser.node.AAndOperandOr;
import ua.kiev.kpi.sc.parser.node.AArrElemElementalExpression;
import ua.kiev.kpi.sc.parser.node.AArrayVariableType;
import ua.kiev.kpi.sc.parser.node.AAssignOperator;
import ua.kiev.kpi.sc.parser.node.ABooleanLiteral;
import ua.kiev.kpi.sc.parser.node.ACallElementalExpression;
import ua.kiev.kpi.sc.parser.node.AConstantClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AConstantDefinition;
import ua.kiev.kpi.sc.parser.node.ACycleCycleOperator;
import ua.kiev.kpi.sc.parser.node.ACycleOperator;
import ua.kiev.kpi.sc.parser.node.ADivSummand;
import ua.kiev.kpi.sc.parser.node.AElseConditionalOperator;
import ua.kiev.kpi.sc.parser.node.AFunctionClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AGtComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AGteqComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AIdentifierElementalExpression;
import ua.kiev.kpi.sc.parser.node.AIntLiteralNumeric;
import ua.kiev.kpi.sc.parser.node.ALtComparisonExpression;
import ua.kiev.kpi.sc.parser.node.ALteqComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AMulSummand;
import ua.kiev.kpi.sc.parser.node.ANegMultiplier;
import ua.kiev.kpi.sc.parser.node.ANormalFunctionBody;
import ua.kiev.kpi.sc.parser.node.AOrExprExpression;
import ua.kiev.kpi.sc.parser.node.ARecursiveElementalExpression;
import ua.kiev.kpi.sc.parser.node.ARemSummand;
import ua.kiev.kpi.sc.parser.node.ASimpleConditionalOperator;
import ua.kiev.kpi.sc.parser.node.ASimpleIf;
import ua.kiev.kpi.sc.parser.node.ASimpleOperator;
import ua.kiev.kpi.sc.parser.node.ASingleBlock;
import ua.kiev.kpi.sc.parser.node.ASubSimpleExpression;
import ua.kiev.kpi.sc.parser.node.AVariableClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AVariableDefinition;
import ua.kiev.kpi.sc.parser.node.AVoidFunctionBody;

public class InterimRepresentationBuilder extends DepthFirstAdapter {
	private LinkedList<Translation> polizStack;
	/**
	 * For delimiting blocks, we're pushing block start label when entering block and popping it when leaving block
	 */
	private LinkedList<Translation> blockLabelsStack;
	private Translation lastElemBeforeThisBlock;

	public InterimRepresentationBuilder() {
		reset();
	}

	public void reset() {
		polizStack = new LinkedList<Translation>();
		blockLabelsStack = new LinkedList<Translation>();
		polizStack.add(new Comment(">>START<<"));
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		Iterator<Translation> it = polizStack.descendingIterator();
		while (it.hasNext()) {			
			Translation t = it.next();
			String comment = null;
			if (t instanceof AbstractTranslation) {
				comment = ((AbstractTranslation) t).getComment();
			}
			if (!(t instanceof InvisibleTranslation)) {
				b.append(t);
			}
			if (comment != null) {
				b.append("\n\n      // ");
				b.append(comment);
				b.append("\n");
			}
			if (it.hasNext() && comment == null) {
				b.append(", ");
			}
		}
		return b.toString();
	}

	/**
	 * while (expr) { block; }
	 * 
	 * is converted to:
	 * 
	 * :LABEL0 expr LABEL1 jmpFalse block LABEL0 jmpAlways :LABEL1
	 * 
	 */
	@Override
	public void inACycleCycleOperator(ACycleCycleOperator node) {
		mark();
		LabelDeclaration label0 = LabelDeclaration.getInstance();
		polizStack.push(label0);
		blockLabelsStack.push(label0);
	}

	@Override
	public void outACycleCycleOperator(ACycleCycleOperator node) {
		// get marker of block start
		Marker marker = (Marker) blockLabelsStack.pop();

		// get block
		Deque<Translation> blockData = popBlock(marker);

		while (blockLabelsStack.peek() instanceof Marker) {
			blockLabelsStack.peek();
		}
				
		LabelDeclaration label0 = (LabelDeclaration) blockLabelsStack.pop();
		LabelDeclaration label1 = LabelDeclaration.getInstance();			
		
		polizStack.push(label1.getPointer());
		polizStack.push(new JumpIfFalse());
		addToPoliz(blockData);
		polizStack.push(label0.getPointer());
		polizStack.push(new JumpAlways());
		polizStack.push(label1);
	}

	private Deque<Translation> popBlock(Marker label0) {
		Deque<Translation> blockData = new LinkedList<Translation>();
		if (!polizStack.isEmpty()) {
			Translation tmp = null;
			do {
				tmp = polizStack.pop();
				blockData.push(tmp);
			} while (tmp != label0 && !polizStack.isEmpty());
			if (blockData.peek() != label0) {
				throw new MyException(
						"Oopsie... something expected on stack was not found");
			} else {
				blockData.pop();
			}
		} else {
			throw new MyException(
					"Oopsie... something expected on stack was not found");
		}
		return blockData;
	}

	private void addToPoliz(Deque<Translation> blockData) {
		while (!blockData.isEmpty()) {
			polizStack.push(blockData.pop());
		}
	}


	@Override
	public void inASingleBlock(ASingleBlock node) {
		Marker marker = Marker.getInstance();
		polizStack.push(marker);
		blockLabelsStack.push(marker);
	}
	
	@Override
	public void outASingleBlock(ASingleBlock node) {
	}
	
	/**
	 * if (expr) { block1; }
	 * 
	 * is translated into: 
	 * expr LABEL0 jmpFalse
	 *     block1 
	 * :LABEL0
	 */
	@Override
	public void inASimpleIf(ASimpleIf node) {
		mark();
	}
	
	@Override
	public void outASimpleIf(ASimpleIf node) {
		// get marker of block start
		Marker marker = (Marker) blockLabelsStack.pop();

		// get block
		Deque<Translation> blockData = popBlock(marker);
		
		LabelDeclaration label0 = LabelDeclaration.getInstance();
		polizStack.push(label0.getPointer());
		polizStack.push(new JumpIfFalse());

		addToPoliz(blockData);

		polizStack.push(label0);
	}

	/**
	 * 
	 * conditional_operator = {simple} simple_if | {else} simple_if else l_brc
	 * block r_brc;
	 * 
	 * if (expr) { block1; } else { block2; }
	 *  * 
	 * is translated into: 
	 * expr LABEL0 jmpFalse
	 *     block1 
	 * LABEL1 jmpAlways
	 * :LABEL0
	 *     block2
	 * :LABEL1
	 * 
	 */
	
	@Override
	public void inASimpleConditionalOperator(ASimpleConditionalOperator node) {
		mark();
	}
	
	@Override
	public void outASimpleConditionalOperator(ASimpleConditionalOperator node) {
		addComment("if ("
				+ ((ASimpleIf) node.getSimpleIf()).getExpression().toString()
				+ ")");
	}
	
	@Override
	public void inAElseConditionalOperator(AElseConditionalOperator node) {
		mark();
	}

	@Override
	public void outAElseConditionalOperator(AElseConditionalOperator node) {
		addComment("if ("
				+ ((ASimpleIf) node.getSimpleIf()).getExpression().toString()
				+ ")");
		
		// get marker of block start
		Marker marker = (Marker) blockLabelsStack.pop();

		// get block
		Deque<Translation> blockData = popBlock(marker);
		LabelDeclaration label0 = (LabelDeclaration) polizStack.pop();
		LabelDeclaration label1 = LabelDeclaration.getInstance();
		polizStack.push(label1.getPointer());
		polizStack.push(new JumpAlways());
		polizStack.push(label0);
		addToPoliz(blockData);
		polizStack.push(label1);	
	}
	

	@Override
	public void outABooleanLiteral(ABooleanLiteral node) {
		polizStack.push(new Literal(node.toString()));
	}

	@Override
	public void outAIntLiteralNumeric(AIntLiteralNumeric node) {
		polizStack.push(new Literal(node.toString()));
	}

	@Override
	public void outAVariableDefinition(AVariableDefinition node) {
		polizStack.push(new Literal(node.getVariableName().toString()));
		if (node.getVariableType() instanceof AArrayVariableType) {
			polizStack.push(new TypePointer(((AArrayVariableType) node
					.getVariableType()).getType()));
			polizStack.push(Operation.DEF_ARR());

		} else {
			polizStack.push(new TypePointer(node.getVariableType()));
			polizStack.push(Operation.DEF_VAR());

		}
	}

	@Override
	public void outAConstantDefinition(AConstantDefinition node) {
		polizStack.push(new Literal(node.getVariableName().toString()));

		if (node.getVariableType() instanceof AArrayVariableType) {
			polizStack.push(new TypePointer(((AArrayVariableType) node
					.getVariableType()).getType()));
			polizStack.push(Operation.DEF_ARR());

		} else {
			polizStack.push(new TypePointer(node.getVariableType()));
			polizStack.push(Operation.DEF_VAR());

		}
		polizStack.push(Operation.MOD_FINAL());
	}

	@Override
	public void inASimpleOperator(ASimpleOperator node) {
		mark();		
	}
	
	@Override
	public void outASimpleOperator(ASimpleOperator node) {
		addComment(node.toString());
	}
	
	@Override
	public void inAAssignOperator(AAssignOperator node) {
		mark();
	}
	
	@Override
	public void outAAssignOperator(AAssignOperator node) {
		addComment(node.toString());
		polizStack.push(new VariablePointer(node.getVariableName()));
		polizStack.push(Operation.ASSIGN());
	}

	@Override
	public void outAOrExprExpression(AOrExprExpression node) {
		polizStack.push(Operation.OR());
	}

	@Override
	public void outAAndOperandOr(AAndOperandOr node) {
		polizStack.push(Operation.AND());
	}

	@Override
	public void outAAddSimpleExpression(AAddSimpleExpression node) {
		polizStack.push(Operation.ADD());
	}

	@Override
	public void outASubSimpleExpression(ASubSimpleExpression node) {
		polizStack.push(Operation.SUB());
	}

	@Override
	public void outAGtComparisonExpression(AGtComparisonExpression node) {
		polizStack.push(Operation.GT());
	}

	@Override
	public void outALtComparisonExpression(ALtComparisonExpression node) {
		polizStack.push(Operation.LT());
	}

	@Override
	public void outALteqComparisonExpression(ALteqComparisonExpression node) {
		polizStack.push(Operation.LE());
	}

	@Override
	public void outAGteqComparisonExpression(AGteqComparisonExpression node) {
		polizStack.push(Operation.GE());
	}

	@Override
	public void outAMulSummand(AMulSummand node) {
		polizStack.push(Operation.MUL());
	}

	@Override
	public void outADivSummand(ADivSummand node) {
		polizStack.push(Operation.DIV());
	}

	@Override
	public void outARemSummand(ARemSummand node) {
		polizStack.push(Operation.MOD());
	}

	@Override
	public void outANegMultiplier(ANegMultiplier node) {
		polizStack.push(Operation.NEGATION());
	}

	/**
	 * TODO: {neg} l_par variable_type r_par cast; Ignore that?
	 */

	@Override
	public void outAArrElemElementalExpression(AArrElemElementalExpression node) {
		polizStack.push(Operation.ARRAY_ACCESS());
	}

	@Override
	public void outACallElementalExpression(ACallElementalExpression node) {
		polizStack.push(new FuncPointer(node.getIdentifier()));
		polizStack.push(Operation.FUNC_CALL());
	}

	@Override
	public void outAIdentifierElementalExpression(
			AIdentifierElementalExpression node) {
		polizStack.push(new VariablePointer(node));
	}

	@Override
	public void outARecursiveElementalExpression(
			ARecursiveElementalExpression node) {
		polizStack.push(new VariablePointer(node.getIdentifier()));
		super.outARecursiveElementalExpression(node);
	}

	/**
	 * TODO: arglist
	 * 
	 * fact_arg_list = {single} expression | {multiple} expression comma
	 * fact_arg_list;
	 */

	@Override
	public void outAVoidFunctionBody(AVoidFunctionBody node) {
		polizStack.push(Operation.EMPTY_RETURN());
	}

	@Override
	public void outANormalFunctionBody(ANormalFunctionBody node) {
		polizStack.push(Operation.RETURN());
	}

	private void mark() {
		lastElemBeforeThisBlock = polizStack.peek();
	}

	private void addComment(String str) {
		if (lastElemBeforeThisBlock instanceof AbstractTranslation) {
			((AbstractTranslation) lastElemBeforeThisBlock).setComment(str);
		}
		lastElemBeforeThisBlock = null;
	}

	@Override
	public void inAConstantClassBodyElem(AConstantClassBodyElem node) {
		mark();
	}

	@Override
	public void inAFunctionClassBodyElem(AFunctionClassBodyElem node) {
		mark();
	}

	@Override
	public void inAVariableClassBodyElem(AVariableClassBodyElem node) {
		mark();
	}

	@Override
	public void inACycleOperator(ACycleOperator node) {
		mark();
	}

	@Override
	public void outAConstantClassBodyElem(AConstantClassBodyElem node) {
		addComment(node.toString());
	}

	@Override
	public void outAVariableClassBodyElem(AVariableClassBodyElem node) {
		addComment(node.toString());
	}

	@Override
	public void outAFunctionClassBodyElem(AFunctionClassBodyElem node) {
		addComment(node.toString());
	}

	@Override
	public void outACycleOperator(ACycleOperator node) {
		addComment(node.toString());
	}
}
