package ua.kiev.kpi.sc.parser.ext.rules;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.ui.Preferences;
import ua.kiev.kpi.sc.parser.node.AAddSimpleExpression;
import ua.kiev.kpi.sc.parser.node.AAndOperandOr;
import ua.kiev.kpi.sc.parser.node.AArrElemElementalExpression;
import ua.kiev.kpi.sc.parser.node.AArrayVariableType;
import ua.kiev.kpi.sc.parser.node.AAssignOperator;
import ua.kiev.kpi.sc.parser.node.ABooleanLiteral;
import ua.kiev.kpi.sc.parser.node.ABooleanType;
import ua.kiev.kpi.sc.parser.node.ACallElementalExpression;
import ua.kiev.kpi.sc.parser.node.AClassBody;
import ua.kiev.kpi.sc.parser.node.ACondOperator;
import ua.kiev.kpi.sc.parser.node.AConstantClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AConstantDefinition;
import ua.kiev.kpi.sc.parser.node.AConstantName;
import ua.kiev.kpi.sc.parser.node.ACycleCycleOperator;
import ua.kiev.kpi.sc.parser.node.ACycleOperator;
import ua.kiev.kpi.sc.parser.node.ADivSummand;
import ua.kiev.kpi.sc.parser.node.ADoubleQuoteClosing;
import ua.kiev.kpi.sc.parser.node.AElseConditionalOperator;
import ua.kiev.kpi.sc.parser.node.AEqOperandAnd;
import ua.kiev.kpi.sc.parser.node.AFracLiteralNumeric;
import ua.kiev.kpi.sc.parser.node.AFunctionClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AFunctionDeclaration;
import ua.kiev.kpi.sc.parser.node.AFunctionDefinition;
import ua.kiev.kpi.sc.parser.node.AFunctionName;
import ua.kiev.kpi.sc.parser.node.AGtComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AGteqComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AIdentifierElementalExpression;
import ua.kiev.kpi.sc.parser.node.AIntLiteralNumeric;
import ua.kiev.kpi.sc.parser.node.AIntType;
import ua.kiev.kpi.sc.parser.node.ALiteralElementalExpression;
import ua.kiev.kpi.sc.parser.node.ALiteralString;
import ua.kiev.kpi.sc.parser.node.ALtComparisonExpression;
import ua.kiev.kpi.sc.parser.node.ALteqComparisonExpression;
import ua.kiev.kpi.sc.parser.node.AMethodName;
import ua.kiev.kpi.sc.parser.node.AMulSummand;
import ua.kiev.kpi.sc.parser.node.AMultiClassSeq;
import ua.kiev.kpi.sc.parser.node.AMultiCompilationUnit;
import ua.kiev.kpi.sc.parser.node.AMultipleFactArgList;
import ua.kiev.kpi.sc.parser.node.AMultipleNVarFormalArgList;
import ua.kiev.kpi.sc.parser.node.ANegCast;
import ua.kiev.kpi.sc.parser.node.ANegMultiplier;
import ua.kiev.kpi.sc.parser.node.ANegativeInteger;
import ua.kiev.kpi.sc.parser.node.ANeqOperandAnd;
import ua.kiev.kpi.sc.parser.node.ANormalFunctionBody;
import ua.kiev.kpi.sc.parser.node.ANotPublicClass;
import ua.kiev.kpi.sc.parser.node.ANothingFormalArgList;
import ua.kiev.kpi.sc.parser.node.ANullLiteral;
import ua.kiev.kpi.sc.parser.node.ANumericLiteral;
import ua.kiev.kpi.sc.parser.node.AOrExprExpression;
import ua.kiev.kpi.sc.parser.node.APositiveSignedInteger;
import ua.kiev.kpi.sc.parser.node.APublicClass;
import ua.kiev.kpi.sc.parser.node.ARealLiteralNumeric;
import ua.kiev.kpi.sc.parser.node.ARecursiveElementalExpression;
import ua.kiev.kpi.sc.parser.node.ARemSummand;
import ua.kiev.kpi.sc.parser.node.AScalarVariableType;
import ua.kiev.kpi.sc.parser.node.ASimpleCast;
import ua.kiev.kpi.sc.parser.node.ASimpleComparisonExpression;
import ua.kiev.kpi.sc.parser.node.ASimpleConditionalOperator;
import ua.kiev.kpi.sc.parser.node.ASimpleExpression;
import ua.kiev.kpi.sc.parser.node.ASimpleIf;
import ua.kiev.kpi.sc.parser.node.ASimpleInteger;
import ua.kiev.kpi.sc.parser.node.ASimpleMultiplier;
import ua.kiev.kpi.sc.parser.node.ASimpleOperandAnd;
import ua.kiev.kpi.sc.parser.node.ASimpleOperandOr;
import ua.kiev.kpi.sc.parser.node.ASimpleOperator;
import ua.kiev.kpi.sc.parser.node.ASimpleSimpleExpression;
import ua.kiev.kpi.sc.parser.node.ASimpleSummand;
import ua.kiev.kpi.sc.parser.node.ASingleBlock;
import ua.kiev.kpi.sc.parser.node.ASingleClassSeq;
import ua.kiev.kpi.sc.parser.node.ASingleCompilationUnit;
import ua.kiev.kpi.sc.parser.node.ASingleFactArgList;
import ua.kiev.kpi.sc.parser.node.ASingleVarFormalArgList;
import ua.kiev.kpi.sc.parser.node.AStringLiteral;
import ua.kiev.kpi.sc.parser.node.AStringType;
import ua.kiev.kpi.sc.parser.node.ASubSimpleExpression;
import ua.kiev.kpi.sc.parser.node.ATypeName;
import ua.kiev.kpi.sc.parser.node.ATypeType;
import ua.kiev.kpi.sc.parser.node.AVariableClassBodyElem;
import ua.kiev.kpi.sc.parser.node.AVariableDefinition;
import ua.kiev.kpi.sc.parser.node.AVariableName;
import ua.kiev.kpi.sc.parser.node.AVariableResultType;
import ua.kiev.kpi.sc.parser.node.AVoidFunctionBody;
import ua.kiev.kpi.sc.parser.node.AVoidResultType;
import ua.kiev.kpi.sc.parser.parser.Parser;

@SuppressWarnings({"unchecked", "serial"})
public class ReduceRulesMapping {
	private static final Map<Class<?>, String> classToRepresentationMap = new HashMap<Class<?>, String>();
	private static final Map<Integer, String> indToRepresentationMap = new HashMap<Integer, String>();
	
	private void put(int ind, Class<?> clazz, String repr) 
	{
		classToRepresentationMap.put(clazz, repr);
		indToRepresentationMap.put(ind, repr);
	}

	{
		
		put(0, ASingleCompilationUnit.class, "compilation_unit = public_class");
		put(1, AMultiCompilationUnit.class, "compilation_unit = public_class class_seq");
		put(2, ASingleClassSeq.class, "class_seq = not_public_class");
		put(3, AMultiClassSeq.class, "class_seq = not_public_class class_seq");
		put(4, APublicClass.class, "public_class = public TClassToken identifier TLBrc class_body TRBrc");
		put(5, ANotPublicClass.class, "not_public_class = TClassToken identifier TLBrc class_body TRBrc");
		put(6, AClassBody.class, "class_body = class_body_elem");
		put(7, AClassBody.class, "class_body = class_body_elem | class_body");
		put(8, AFunctionClassBodyElem.class, "class_body_elem = function_definition");
		put(9, AVariableClassBodyElem.class, "class_body_elem = variable_definition TSemi");
		put(10, AConstantClassBodyElem.class, "class_body_elem = constant_definition TSemi");
		put(11, AFunctionDefinition.class, "function_definition = function_declaration function_body");
		put(12, AVariableDefinition.class, "variable_definition = variable_type variable_name");
		put(13, AConstantDefinition.class, "constant_definition = TFinal variable_type variable_name TAssign literal");
		put(14, AStringLiteral.class, "literal = literal_string");
		put(15, ABooleanLiteral.class, "literal = literal_boolean");
		put(16, ANumericLiteral.class, "literal = literal_numeric");
		put(17, ANullLiteral.class, "literal = literal_null");
		put(18, ALiteralString.class, "literal_string = double_quote char* double_quote_closing");
		put(19, ALiteralString.class, "literal_string = double_quote char* double_quote_closing;");
		put(20, ADoubleQuoteClosing.class, "double_quote_closing = TDoubleQuote");
		put(21, AIntLiteralNumeric.class, "literal_numeric = integer");
		put(22, AFracLiteralNumeric.class, "literal_numeric = TDot non_negative_integer");
		put(23, ARealLiteralNumeric.class, "literal_numeric = integer TDot non_negative_integer");
		put(24, ASimpleInteger.class, "integer = non_negative_integer");
		put(25, APositiveSignedInteger.class, "integer = TPlus non_negative_integer");
		put(26, ANegativeInteger.class, "integer = minus non_negative_integer");
		put(27, AVariableName.class, "variable_name = TIdentifier");
		put(28, AFunctionName.class, "variable_name = TIdentifier");
		put(29, AFunctionDeclaration.class, "function_declaration = TPublic result_type function_name TLPar formal_arg_list TRPar");
		put(30, AVoidResultType.class, "result_type = TVoid");
		put(31, AVariableResultType.class, "result_type = variable_type");
		put(32, AScalarVariableType.class, "variable_type = type");
		put(33, AArrayVariableType.class, "variable_type = type TLBkt TRBkt");
		put(34, ABooleanType.class, "type = TBoolean");
		put(35, AIntType.class, "type = TInt");
		put(36, AStringType.class, "type = TStringToken");
		put(37, ATypeType.class, "type = TIdentifier");
		put(38, ATypeName.class, "type_name = type_name");
		put(39, ANothingFormalArgList.class, "formal_arg_list = ");
		put(40, ASingleVarFormalArgList.class, "expression");
		put(41, AMultipleNVarFormalArgList.class, "expression comma fact_arg_list");
		put(42, AMethodName.class, "method_name = identifier");
		put(43, AConstantName.class, "constant_name = identifier");
		put(44, ANormalFunctionBody.class, "function_body = TLBrc block return expression TSemi TRBrc");
		put(45, AVoidFunctionBody.class, "function_body = TLBrc block return TSemi TRBrc");
		put(46, ASingleBlock.class, "block = operator");
		put(47, ASingleBlock.class, "block = operator | block");
		put(48, ASimpleOperator.class, "operator = expression TSemi");
		put(49, AAssignOperator.class, "operator = variable_name TAssign expression TSemi");
		put(50, ACondOperator.class, "operator = conditional_operator");
		put(51, ACycleOperator.class, "cycle_operator = while TLPar expression TRPar TLBrc block TRBrc");
		put(52, ASimpleIf.class, "simple_if = TIf TLPar expression TRPar TLBrc block TRBrc");
		put(53, ASimpleConditionalOperator.class, "conditional_operator = simple_if TElse TLBrc block TRBrc");
		put(54, AElseConditionalOperator.class, "simple_if else TLBrc block TRBrc");
		put(55, ACycleCycleOperator.class, "operator = cycle_operator");
		put(56, ASimpleExpression.class, "expression = operand_or");
		put(57, AOrExprExpression.class, "expression = operand_or TBarBar expression");
		put(58, ASimpleOperandOr.class, "operand_or = operand_and");
		put(59, AAndOperandOr.class, "operand_or = operand_and TAmpAmp operand_or");
		put(60, ASimpleOperandAnd.class, "operand_and = comparison_expression");
		put(61, AEqOperandAnd.class, "operand_and = comparison_expression TEq operand_and");
		put(62, ANeqOperandAnd.class, "operand_and = comparison_expression TNeq operand_and");
		put(63, ASimpleComparisonExpression.class, "comparison_expression = simple_expression");
		put(64, AGtComparisonExpression.class, "comparison_expression = simple_expression TGt comparison_expression");
		put(65, ALtComparisonExpression.class, "comparison_expression = simple_expression TLt comparison_expression");
		put(66, ALteqComparisonExpression.class, "comparison_expression = simple_expression TLteq comparison_expression");
		put(67, AGteqComparisonExpression.class, "comparison_expression = simple_expression TGteq comparison_expression");
		put(68, ASimpleSimpleExpression.class, "simple_expression = summand");
		put(69, AAddSimpleExpression.class, "simple_expression = summand TPlus simple_expression");
		put(70, ASubSimpleExpression.class, "simple_expression = summand TMinus simple_expression");
		put(71, ASimpleSummand.class, "summand = multiplier");
		put(72, AMulSummand.class, "summand = multiplier TStar summand");
		put(73, ADivSummand.class, "summand = multiplier TSlash summand");
		put(74, ARemSummand.class, "summand = multiplier TPercent summand");
		put(75, ASimpleMultiplier.class, "multiplier = cast");
		put(76, ANegMultiplier.class, "multiplier = TEmark multiplier");
		put(77, ASimpleCast.class, "elemental_expression");
		put(78, ANegCast.class, "TLPar variable_type TRPar cast");
		put(79, ARecursiveElementalExpression.class, "elemental_expression = TIdentifier TDot elemental_expression");
		put(80, AArrElemElementalExpression.class, "elemental_expression = TIdentifier TLBkt expression TRBkt");
		put(81, ACallElementalExpression.class, "elemental_expression = TIdentifier TLPar fact_arg_list TRPar");
		put(82, ALiteralElementalExpression.class, "elemental_expression = literal");
		put(83, AIdentifierElementalExpression.class, "elemental_expression =  TIdentifier");
		put(84, ASingleFactArgList.class, "fact_arg_list = expression");
		put(85, AMultipleFactArgList.class, "fact_arg_list = expression TComma fact_arg_list");
	}

	public static String getRepresentation(List<?> array) {
		String result;
		if (array == null || array.size() == 0) {
			result = "";
		} else {
			Object obj = array.get(0);
			result = classToRepresentationMap.get(obj.getClass());
			if (obj instanceof List) {
				result = "";
			}
			if (result == null) {
				result = obj.getClass().getSimpleName();
			}
		}
		if (!result.contains("\n") && !"".equals(result)) {
			result = result + "\n";
		}
		return result;
	}
	
	public static String getRepresentation(int ruleInd) {	
		String result = indToRepresentationMap.get(ruleInd);
		if (result == null) {
			if (Preferences.productionMode) {
				result = "";
			} else {
				result = "> STRANGE <";
			}
		}
		result = String.format("%1$2d :: %2$s", ruleInd, result);
		return result;
	}

	public static String[][] createActionTable(int[][][] actionTable) {
		LinkedList table = new LinkedList();
		int currNum = 0;

		for (int stateNum = 0; stateNum < actionTable.length; stateNum++) {
			int[][] currStateActions = actionTable[stateNum];
			for (int currStActNum = 0; currStActNum < currStateActions.length; currStActNum++) {
				int[] actionParams = currStateActions[currStActNum];
				String[] tmp = new String[4];
				table.add(tmp);
				tmp[0] = String.valueOf(stateNum);
				tmp[1] = actionParams[0] == -1 // terminal idx
				? ""
						: String.valueOf(actionParams[0]);
				tmp[2] = ActionTypes.lookup(actionParams[1]); // ACCEPT/REDUCE/SHIFT/ERROR
				tmp[3] = actionParams[2] == -1 // symbol idx
				? "" // gotoLookup/reductionRule
						: String.valueOf(actionParams[2]);
				currNum++;
			}
		}
		return (String[][]) table.toArray(new String[0][0]);
	}

	/*
	 * symbol idx
	 */
	public static String[][] createGotoTable(int[][][] gotoTable) {
		LinkedList table = new LinkedList();
		int currNum = 0;

		// 1 dim is symbol idx
		for (int symbolNum = 0; symbolNum < gotoTable.length; symbolNum++) {
			int[][] currStateActions = gotoTable[symbolNum];
			for (int currStActNum = 0; currStActNum < currStateActions.length; currStActNum++) {
				int[] actionParams = currStateActions[currStActNum];
				String[] tmp = new String[3];
				table.add(tmp);
				tmp[0] = String.valueOf(symbolNum); // symbol idx
				tmp[1] = actionParams[0] == -1 // curr state
				? ""
						: String.valueOf(actionParams[0]);
				tmp[2] = actionParams[1] == -1 // state to add
				? ""
						: String.valueOf(actionParams[1]);
				currNum++;
			}
		}
		return (String[][]) table.toArray(new String[0][0]);
	}

	public static TableModel createActionTableTableModel(int[][][] actionTable) {
		DefaultTableModel res = new DefaultTableModel(
				createActionTable(actionTable), new String[] { "Curr state",
						"Symbol idx", "Action type", "Rule\\Push\\goto" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return res;
	}

	public static TableModel createGotoTableTableModel(int[][][] gotoTable) {
		DefaultTableModel res = new DefaultTableModel(
				createGotoTable(gotoTable), new String[] { "Symbol idx",
						"Curr state", "Push idx" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return res;
	}

	public static TableModel createActionModel() {
		return createActionTableTableModel(Parser.actionTable);
	}

	public static TableModel createGotoModel() {
		return createGotoTableTableModel(Parser.gotoTable);
	}

	public enum ActionTypes {
		SHIFT, REDUCE, ACCEPT, ERROR;
		public static String lookup(int ordinal) {
			for (ActionTypes val : ActionTypes.values()) {
				if (ordinal == val.ordinal()) {
					return val.toString();
				}
			}
			throw new MyException("Parser internal error " + ordinal);
		}
	}

}
