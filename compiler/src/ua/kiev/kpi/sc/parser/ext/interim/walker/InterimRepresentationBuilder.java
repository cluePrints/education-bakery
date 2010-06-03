package ua.kiev.kpi.sc.parser.ext.interim.walker;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import ua.kiev.kpi.sc.parser.analysis.DepthFirstAdapter;
import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.AbstractTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.InvisibleTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Comment;
import ua.kiev.kpi.sc.parser.ext.interim.repr.FuncPointer;
import ua.kiev.kpi.sc.parser.ext.interim.repr.JumpAlways;
import ua.kiev.kpi.sc.parser.ext.interim.repr.JumpIfFalse;
import ua.kiev.kpi.sc.parser.ext.interim.repr.LabelDeclaration;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Literal;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Marker;
import ua.kiev.kpi.sc.parser.ext.interim.repr.TypePointer;
import ua.kiev.kpi.sc.parser.ext.interim.repr.VariablePointer;
import ua.kiev.kpi.sc.parser.ext.interim.repr.op.Operation;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Bound;
import ua.kiev.kpi.sc.parser.ext.ui.Preferences;
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
import ua.kiev.kpi.sc.parser.node.AEqOperandAnd;
import ua.kiev.kpi.sc.parser.node.AFunctionClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AFunctionDeclaration;
import ua.kiev.kpi.sc.parser.node.AGtComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AGteqComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AIdentifierElementalExpression;
import ua.kiev.kpi.sc.parser.node.AIntLiteralNumeric;
import ua.kiev.kpi.sc.parser.node.ALtComparisonExpression;
import ua.kiev.kpi.sc.parser.node.ALteqComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AMulSummand;
import ua.kiev.kpi.sc.parser.node.ANegMultiplier;
import ua.kiev.kpi.sc.parser.node.ANeqOperandAnd;
import ua.kiev.kpi.sc.parser.node.ANormalFunctionBody;
import ua.kiev.kpi.sc.parser.node.ANotPublicClass;
import ua.kiev.kpi.sc.parser.node.AOrExprExpression;
import ua.kiev.kpi.sc.parser.node.APublicClass;
import ua.kiev.kpi.sc.parser.node.ARecursiveElementalExpression;
import ua.kiev.kpi.sc.parser.node.ARemSummand;
import ua.kiev.kpi.sc.parser.node.ASimpleConditionalOperator;
import ua.kiev.kpi.sc.parser.node.ASimpleIf;
import ua.kiev.kpi.sc.parser.node.ASimpleOperandOr;
import ua.kiev.kpi.sc.parser.node.ASimpleOperator;
import ua.kiev.kpi.sc.parser.node.ASingleBlock;
import ua.kiev.kpi.sc.parser.node.ASubSimpleExpression;
import ua.kiev.kpi.sc.parser.node.AVariableClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AVariableDefinition;
import ua.kiev.kpi.sc.parser.node.AVoidFunctionBody;
import ua.kiev.kpi.sc.parser.node.Node;

import com.google.common.collect.Lists;

public class InterimRepresentationBuilder extends DepthFirstAdapter {
	private LinkedList<Translation> polizStack;

	/**
	 * For delimiting blocks, we're pushing block start label when entering block and popping it when leaving block
	 */
	private LinkedList<Translation> blockLabelsStack;
	private LinkedList<Translation> lastElemBeforeThisBlock;

	public InterimRepresentationBuilder() {
		reset();
	}

	public void reset() {
		polizStack = new LinkedList<Translation>();
		blockLabelsStack = new LinkedList<Translation>();
		lastElemBeforeThisBlock = new LinkedList<Translation>();
		polizStack.add(new Comment(">>START<<"));
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		Iterator<Translation> it = getPolizStack().descendingIterator();
		while (it.hasNext()) {			
			Translation t = it.next();
			String comment = null;
			if (t instanceof AbstractTranslation) {
				comment = ((AbstractTranslation) t).getComment();
			}
			boolean isTranslationVisible = !(t instanceof InvisibleTranslation);
			if (isTranslationVisible) {
				b.append(t);
			}
			if (comment != null && Preferences.showCommentsForInterim) {
				b.append("\n\n      // ");
				b.append(comment);
				b.append("\n");
			}
			if (it.hasNext() && comment == null && isTranslationVisible) {
				b.append(", ");
			}
		}
		return b.toString();
	}
	
	@Override
	public void inAIdentifierElementalExpression(
			AIdentifierElementalExpression node) {
		// TODO: check if is not reserved word
		// TODO: equality operator
		// TODO: 5+true
		// TODO: &&, ||		
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
		addToPoliz(label0);
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
		
		addToPoliz(label1.getPointer());
		addToPoliz(new JumpIfFalse());
		addToPoliz(blockData);
		addToPoliz(label0.getPointer());
		addToPoliz(new JumpAlways());
		addToPoliz(label1);
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
			addToPoliz(blockData.pop());
		}
	}


	@Override
	public void inASingleBlock(ASingleBlock node) {
		Marker marker = Marker.getInstance();
		addToPoliz(marker);
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
		addToPoliz(label0.getPointer());
		addToPoliz(new JumpIfFalse());

		addToPoliz(blockData);

		addToPoliz(label0);
	}

	private void addToPoliz(Translation t) {
		polizStack.push(t);
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
				+ ")"
				+ " {...} else {...}");
		
		// get marker of block start
		Marker marker = (Marker) blockLabelsStack.pop();

		// get block
		Deque<Translation> blockData = popBlock(marker);
		LabelDeclaration label0 = (LabelDeclaration) polizStack.pop();
		LabelDeclaration label1 = LabelDeclaration.getInstance();
		addToPoliz(label1.getPointer());
		addToPoliz(new JumpAlways());
		addToPoliz(label0);
		addToPoliz(blockData);
		addToPoliz(label1);	
	}
	

	@Override
	public void outABooleanLiteral(ABooleanLiteral node) {
		addToPoliz(new Literal(node.toString(), TypeSymbol.T_BOOLEAN));
	}

	@Override
	public void outAIntLiteralNumeric(AIntLiteralNumeric node) {
		addToPoliz(new Literal(node.toString(), TypeSymbol.T_INT));
	}

	@Override
	public void outAVariableDefinition(AVariableDefinition node) {
		addToPoliz(new Literal(node.getVariableName().toString(), TypeSymbol.T_STRING));
		if (node.getVariableType() instanceof AArrayVariableType) {
			addToPoliz(new TypePointer(((AArrayVariableType) node
					.getVariableType()).getType()));
			addToPoliz(Operation.DEF_ARR());

		} else {
			addToPoliz(new TypePointer(node.getVariableType()));
			addToPoliz(Operation.DEF_VAR());

		}
	}

	@Override
	public void outAConstantDefinition(AConstantDefinition node) {
		addToPoliz(new Literal(node.getVariableName().toString(), TypeSymbol.T_STRING));

		if (node.getVariableType() instanceof AArrayVariableType) {
			addToPoliz(new TypePointer(((AArrayVariableType) node
					.getVariableType()).getType()));
			addToPoliz(Operation.DEF_ARR());

		} else {
			addToPoliz(new TypePointer(node.getVariableType()));
			addToPoliz(Operation.DEF_VAR());

		}
		addToPoliz(Operation.MOD_FINAL());
	}
	
	@Override
	public void inASimpleOperandOr(ASimpleOperandOr node) {
		addToPoliz(Bound.EXPR_START);
	}
	
	@Override
	public void outASimpleOperandOr(ASimpleOperandOr node) {
		addToPoliz(Bound.EXPR_END);
	}
	
	@Override
	public void inASimpleOperator(ASimpleOperator node) {
		addToPoliz(Bound.EXPR_START);
	}	
	
	@Override
	public void outASimpleOperator(ASimpleOperator node) {
		addToPoliz(Bound.EXPR_END);
	}
	
	@Override
	public void inAAssignOperator(AAssignOperator node) {
		mark();
		addToPoliz(Bound.EXPR_START);
	}
	
	@Override
	public void outAAssignOperator(AAssignOperator node) {
		addComment(node.toString());
		addToPoliz(new VariablePointer(node.getVariableName()));
		addToPoliz(Operation.ASSIGN());
		addToPoliz(Bound.EXPR_END);
	}
	
	@Override
	public void outAEqOperandAnd(AEqOperandAnd node) {
		addToPoliz(Operation.EQ());
	}
	
	@Override
	public void outANeqOperandAnd(ANeqOperandAnd node) {
		addToPoliz(Operation.EQ());
		addToPoliz(Operation.NEGATION());
	}


	@Override
	public void outAOrExprExpression(AOrExprExpression node) {
		addToPoliz(Operation.OR());
	}

	@Override
	public void outAAndOperandOr(AAndOperandOr node) {
		addToPoliz(Operation.AND());
	}

	@Override
	public void outAAddSimpleExpression(AAddSimpleExpression node) {
		addToPoliz(Operation.ADD());
	}

	@Override
	public void outASubSimpleExpression(ASubSimpleExpression node) {
		addToPoliz(Operation.SUB());
	}

	@Override
	public void outAGtComparisonExpression(AGtComparisonExpression node) {
		addToPoliz(Operation.GT());
	}

	@Override
	public void outALtComparisonExpression(ALtComparisonExpression node) {
		addToPoliz(Operation.LT());
	}

	@Override
	public void outALteqComparisonExpression(ALteqComparisonExpression node) {
		addToPoliz(Operation.LE());
	}

	@Override
	public void outAGteqComparisonExpression(AGteqComparisonExpression node) {
		addToPoliz(Operation.GE());
	}

	@Override
	public void outAMulSummand(AMulSummand node) {
		addToPoliz(Operation.MUL());
	}

	@Override
	public void outADivSummand(ADivSummand node) {
		addToPoliz(Operation.DIV());
	}

	@Override
	public void outARemSummand(ARemSummand node) {
		addToPoliz(Operation.MOD());
	}

	@Override
	public void outANegMultiplier(ANegMultiplier node) {
		addToPoliz(Operation.NEGATION());
	}

	/**
	 * TODO: {neg} l_par variable_type r_par cast; Ignore that?
	 */

	@Override
	public void outAArrElemElementalExpression(AArrElemElementalExpression node) {
		addToPoliz(Operation.ARRAY_ACCESS());
	}

	@Override
	public void outACallElementalExpression(ACallElementalExpression node) {
		addToPoliz(new FuncPointer(node.getIdentifier()));
		int count = calcArguments(node);
		addToPoliz(new Literal(String.valueOf(count), TypeSymbol.T_INT));
		addToPoliz(Operation.FUNC_CALL());
	}

	@Override
	public void outAIdentifierElementalExpression(
			AIdentifierElementalExpression node) {
		addToPoliz(new VariablePointer(node));
	}
	
	@Override
	public void inARecursiveElementalExpression(
			ARecursiveElementalExpression node) {
		addToPoliz(new VariablePointer(node.getIdentifier()));
	}

	@Override
	public void outARecursiveElementalExpression(
			ARecursiveElementalExpression node) {
	}

	@Override
	public void outAVoidFunctionBody(AVoidFunctionBody node) {
		addToPoliz(Operation.EMPTY_RETURN());
	}

	@Override
	public void outANormalFunctionBody(ANormalFunctionBody node) {
		addToPoliz(Operation.RETURN());
	}

	private void mark() {		
		lastElemBeforeThisBlock.push(polizStack.peek());
	}

	private void addComment(String str) {		
		if (lastElemBeforeThisBlock.peek() instanceof AbstractTranslation) {
			((AbstractTranslation) lastElemBeforeThisBlock.peek()).setComment(str);
		}
		lastElemBeforeThisBlock.pop();
	}

	@Override
	public void inAConstantClassBodyElem(AConstantClassBodyElem node) {
		mark();
	}
	
	/**
	 * 
	 * function_declaration = 
		public result_type function_name l_par formal_arg_list r_par;
		
			function_definition =
		function_declaration function_body;
		
		:LABEL0 result_type name param_count type1 name1 DEF_VAR type2 name2 DEF_VAR
		  function_body
		LABEL0 DEF_FUNC
	 */
	@Override
	public void inAFunctionDeclaration(AFunctionDeclaration node) {		
		addToPoliz(new TypePointer(node.getResultType()));
		addToPoliz(new Literal(node.getFunctionName().toString(), TypeSymbol.T_STRING));
		int count;
		// TODO: fix / pray about this - special place in hell reserved for such coders
		count = calcArguments(node);		
		
		addToPoliz(new Literal(String.valueOf(count), TypeSymbol.T_INT));
	}

	private int calcArguments(Node node) {
		int count;
		String str = node.toString().replace(" ", "");
		if (str.contains("()")) {
			count = 0;
		} else {
			count = 1;
			for (int i=0; i<str.length(); i++) {
				if (str.charAt(i) == ',') {
					count++;
				}
			}
		}
		return count;
	}	
	@Override
	public void outAFunctionDeclaration(AFunctionDeclaration node) {
		addComment(node.toString());
	}

	@Override
	public void inAFunctionClassBodyElem(AFunctionClassBodyElem node) {
		mark();
		Marker marker = Marker.getInstance();
		addToPoliz(marker);
		blockLabelsStack.push(marker);
	}
	
	@Override
	public void outAFunctionClassBodyElem(AFunctionClassBodyElem node) {
		Marker m = (Marker) blockLabelsStack.pop();
		Deque<Translation> block = popBlock(m);
		Marker m2 = (Marker)blockLabelsStack.pop();
		Deque<Translation> decl = popBlock(m2);
		LabelDeclaration label0 = LabelDeclaration.getInstance();
		addToPoliz(label0);
		addToPoliz(decl);
		addToPoliz(block);
		addToPoliz(label0.getPointer());
		addToPoliz(Operation.FUNC_DECL());
	}
	
	@Override
	public void inAPublicClass(APublicClass node) {
		addCommentNow(" class "+node.getIdentifier().toString()+ " {");
		Marker marker = Marker.getInstance();
		addToPoliz(marker);
		blockLabelsStack.push(marker);
	}

	private void addCommentNow(String text) {
		mark();
		addComment(text);
	}
		
	@Override
	public void outAPublicClass(APublicClass node) {
		String className = node.getIdentifier().toString();
		outAClass(className);
	}
	
	@Override
	public void inANotPublicClass(ANotPublicClass node) {
		addCommentNow(" class "+node.getIdentifier().toString()+ " {");
		Marker marker = Marker.getInstance();
		addToPoliz(marker);
		blockLabelsStack.push(marker);
	}
	
	@Override
	public void outANotPublicClass(ANotPublicClass node) {
		String className = node.getIdentifier().toString();
		outAClass(className);
	}
	
	private void outAClass(String className) {
		addCommentNow("END class "+className);
		Marker m = (Marker) blockLabelsStack.pop();
		Deque<Translation> block = popBlock(m);
		LabelDeclaration label0 = LabelDeclaration.getInstance();
		addToPoliz(label0);		
		addToPoliz(new Literal(className, TypeSymbol.T_STRING));
		addToPoliz(block);		
		addToPoliz(label0.getPointer());
		addToPoliz(Operation.CLASS_DECL());
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
	public void outACycleOperator(ACycleOperator node) {
		addComment(node.toString());
	}
	
	public LinkedList<Translation> getPolizStack() {
		//return getFilteredPolizStack();
		return Lists.newLinkedList(polizStack);
	}
	
	public LinkedList<Translation> getFilteredPolizStack() {
		LinkedList<Translation> result = Lists.newLinkedList();
		Bound bound = null;
		for (Translation t : polizStack) {
			if (t instanceof Bound) {
				if (bound == null || bound != t){
					bound = (Bound) t;
					result.add(bound);
				}
			} else if (!(t instanceof InvisibleTranslation)) {
				result.add(t);
			}
		}
		return result;
	}
}
