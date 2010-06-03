package ua.kiev.kpi.sc.parser.ext.interim.walker;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.ScopeTreeChecker;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.AbstractTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.InvisibleTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.ScopeAware;
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
import ua.kiev.kpi.sc.parser.ext.scope.Scope;
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

public class InterimRepresentationBuilder extends ScopeTreeChecker {
	private LinkedList<Translation> polizStack;

	/**
	 * For delimiting blocks, we're pushing block start label when entering block and popping it when leaving block
	 */
	private LinkedList<Translation> blockLabelsStack;
	private LinkedList<Translation> lastElemBeforeThisBlock;

	public InterimRepresentationBuilder(Scope scope) {
		super(scope);
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
		super.inAIdentifierElementalExpression(node);
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
		super.inACycleCycleOperator(node);
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
		copyToPoliz(blockData);
		addToPoliz(label0.getPointer());
		addToPoliz(new JumpAlways());
		addToPoliz(label1);
		super.outACycleCycleOperator(node);
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

	private void copyToPoliz(Deque<Translation> blockData) {
		while (!blockData.isEmpty()) {
			addToPoliz(blockData.pop(), false);
		}
	}


	@Override
	public void inASingleBlock(ASingleBlock node) {
		super.inASingleBlock(node);
		Marker marker = Marker.getInstance();
		addToPoliz(marker);
		blockLabelsStack.push(marker);
	}
	
	@Override
	public void outASingleBlock(ASingleBlock node) {
		super.outASingleBlock(node);
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
		super.inASimpleIf(node);
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

		copyToPoliz(blockData);

		addToPoliz(label0);
		super.outASimpleIf(node);
	}

	private void addToPoliz(Translation t) {
		addToPoliz(t, true);
	}
	
	private void addToPoliz(Translation t, boolean injectScope) {
		if (injectScope && t instanceof ScopeAware) {
			((ScopeAware) t).setScope(getCurrentScope());
		}
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
		super.inASimpleConditionalOperator(node);
		mark();
	}
	
	@Override
	public void outASimpleConditionalOperator(ASimpleConditionalOperator node) {		
		addComment("if ("
				+ ((ASimpleIf) node.getSimpleIf()).getExpression().toString()
				+ ")");
		super.outASimpleConditionalOperator(node);
	}
	
	@Override
	public void inAElseConditionalOperator(AElseConditionalOperator node) {
		super.inAElseConditionalOperator(node);
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
		copyToPoliz(blockData);
		addToPoliz(label1);	
		super.outAElseConditionalOperator(node);
	}
	

	@Override
	public void outABooleanLiteral(ABooleanLiteral node) {		
		addToPoliz(new Literal(node.toString(), TypeSymbol.T_BOOLEAN));
		super.outABooleanLiteral(node);
	}

	@Override
	public void outAIntLiteralNumeric(AIntLiteralNumeric node) {
		addToPoliz(new Literal(node.toString(), TypeSymbol.T_INT));
		super.outAIntLiteralNumeric(node);		
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
		super.outAVariableDefinition(node);
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
		super.outAConstantDefinition(node);
	}
	
	@Override
	public void inASimpleOperandOr(ASimpleOperandOr node) {
		super.inASimpleOperandOr(node);
		addToPoliz(Bound.EXPR_START);
	}
	
	@Override
	public void outASimpleOperandOr(ASimpleOperandOr node) {		
		addToPoliz(Bound.EXPR_END);
		super.outASimpleOperandOr(node);
	}
	
	@Override
	public void inASimpleOperator(ASimpleOperator node) {
		super.inASimpleOperator(node);
		addToPoliz(Bound.EXPR_START);
	}	
	
	@Override
	public void outASimpleOperator(ASimpleOperator node) {		
		addToPoliz(Bound.EXPR_END);
		super.outASimpleOperator(node);
	}
	
	@Override
	public void inAAssignOperator(AAssignOperator node) {
		super.inAAssignOperator(node);
		mark();
		addToPoliz(Bound.EXPR_START);
	}
	
	@Override
	public void outAAssignOperator(AAssignOperator node) {		
		addComment(node.toString());
		addToPoliz(new VariablePointer(node.getVariableName()));
		addToPoliz(Operation.ASSIGN());
		addToPoliz(Bound.EXPR_END);
		super.outAAssignOperator(node);
	}
	
	@Override
	public void outAEqOperandAnd(AEqOperandAnd node) {		
		addToPoliz(Operation.EQ());
		super.outAEqOperandAnd(node);
	}
	
	@Override
	public void outANeqOperandAnd(ANeqOperandAnd node) {		
		addToPoliz(Operation.EQ());
		addToPoliz(Operation.NEGATION());
		super.outANeqOperandAnd(node);
	}


	@Override
	public void outAOrExprExpression(AOrExprExpression node) {	
		addToPoliz(Operation.OR());
		super.outAOrExprExpression(node);
	}

	@Override
	public void outAAndOperandOr(AAndOperandOr node) {		
		addToPoliz(Operation.AND());
		super.outAAndOperandOr(node);
	}

	@Override
	public void outAAddSimpleExpression(AAddSimpleExpression node) {		
		addToPoliz(Operation.ADD());
		super.outAAddSimpleExpression(node);
	}

	@Override
	public void outASubSimpleExpression(ASubSimpleExpression node) {		
		addToPoliz(Operation.SUB());
		super.outASubSimpleExpression(node);
	}

	@Override
	public void outAGtComparisonExpression(AGtComparisonExpression node) {		
		addToPoliz(Operation.GT());
		super.outAGtComparisonExpression(node);
	}

	@Override
	public void outALtComparisonExpression(ALtComparisonExpression node) {		
		addToPoliz(Operation.LT());
		super.outALtComparisonExpression(node);
	}

	@Override
	public void outALteqComparisonExpression(ALteqComparisonExpression node) {		
		addToPoliz(Operation.LE());
		super.outALteqComparisonExpression(node);
	}

	@Override
	public void outAGteqComparisonExpression(AGteqComparisonExpression node) {		
		addToPoliz(Operation.GE());
		super.outAGteqComparisonExpression(node);
	}

	@Override
	public void outAMulSummand(AMulSummand node) {		
		addToPoliz(Operation.MUL());
		super.outAMulSummand(node);
	}

	@Override
	public void outADivSummand(ADivSummand node) {		
		addToPoliz(Operation.DIV());
		super.outADivSummand(node);
	}

	@Override
	public void outARemSummand(ARemSummand node) {			
		addToPoliz(Operation.MOD());
		super.outARemSummand(node);
	}

	@Override
	public void outANegMultiplier(ANegMultiplier node) {		
		addToPoliz(Operation.NEGATION());
		super.outANegMultiplier(node);
	}

	/**
	 * TODO: {neg} l_par variable_type r_par cast; Ignore that?
	 */

	@Override
	public void outAArrElemElementalExpression(AArrElemElementalExpression node) {		
		addToPoliz(Operation.ARRAY_ACCESS());
		super.outAArrElemElementalExpression(node);
	}

	@Override
	public void outACallElementalExpression(ACallElementalExpression node) {		
		addToPoliz(new FuncPointer(node.getIdentifier()));
		int count = calcArguments(node);
		addToPoliz(new Literal(String.valueOf(count), TypeSymbol.T_INT));
		addToPoliz(Operation.FUNC_CALL());
		super.outACallElementalExpression(node);
	}

	@Override
	public void outAIdentifierElementalExpression(
			AIdentifierElementalExpression node) {		
		addToPoliz(new VariablePointer(node));
		super.outAIdentifierElementalExpression(node);
	}
	
	@Override
	public void inARecursiveElementalExpression(
			ARecursiveElementalExpression node) {
		super.inARecursiveElementalExpression(node);
		addToPoliz(new VariablePointer(node.getIdentifier()));
	}

	@Override
	public void outARecursiveElementalExpression(
			ARecursiveElementalExpression node) {
		super.outARecursiveElementalExpression(node);
	}

	@Override
	public void outAVoidFunctionBody(AVoidFunctionBody node) {		
		addToPoliz(Operation.EMPTY_RETURN());
		super.outAVoidFunctionBody(node);
	}

	@Override
	public void outANormalFunctionBody(ANormalFunctionBody node) {		
		addToPoliz(Operation.RETURN());
		super.outANormalFunctionBody(node);
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
		super.inAConstantClassBodyElem(node);
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
		super.inAFunctionDeclaration(node);
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
		super.outAFunctionDeclaration(node);
	}

	@Override
	public void inAFunctionClassBodyElem(AFunctionClassBodyElem node) {
		super.inAFunctionClassBodyElem(node);
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
		copyToPoliz(decl);
		copyToPoliz(block);
		addToPoliz(label0.getPointer());
		addToPoliz(Operation.FUNC_DECL());
		super.outAFunctionClassBodyElem(node);		
	}
	
	@Override
	public void inAPublicClass(APublicClass node) {
		super.inAPublicClass(node);
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
		super.outAPublicClass(node);
	}
	
	@Override
	public void inANotPublicClass(ANotPublicClass node) {
		super.inANotPublicClass(node);
		addCommentNow(" class "+node.getIdentifier().toString()+ " {");
		Marker marker = Marker.getInstance();
		addToPoliz(marker);
		blockLabelsStack.push(marker);
	}
	
	@Override
	public void outANotPublicClass(ANotPublicClass node) {		
		String className = node.getIdentifier().toString();
		outAClass(className);
		super.outANotPublicClass(node);
	}
	
	private void outAClass(String className) {
		addCommentNow("END class "+className);
		Marker m = (Marker) blockLabelsStack.pop();
		Deque<Translation> block = popBlock(m);
		LabelDeclaration label0 = LabelDeclaration.getInstance();
		addToPoliz(label0);		
		addToPoliz(new Literal(className, TypeSymbol.T_STRING));
		copyToPoliz(block);		
		addToPoliz(label0.getPointer());
		addToPoliz(Operation.CLASS_DECL());
	}

	@Override
	public void inAVariableClassBodyElem(AVariableClassBodyElem node) {
		super.inAVariableClassBodyElem(node);
		mark();
	}

	@Override
	public void inACycleOperator(ACycleOperator node) {
		super.inACycleOperator(node);
		mark();
	}

	@Override
	public void outAConstantClassBodyElem(AConstantClassBodyElem node) {		
		addComment(node.toString());
		super.outAConstantClassBodyElem(node);
	}

	@Override
	public void outAVariableClassBodyElem(AVariableClassBodyElem node) {		
		addComment(node.toString());
		super.outAVariableClassBodyElem(node);
	}

	@Override
	public void outACycleOperator(ACycleOperator node) {		
		addComment(node.toString());
		super.outACycleOperator(node);
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
