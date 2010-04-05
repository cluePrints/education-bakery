package ua.kiev.kpi.sc.parser.ext.rules;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ua.kiev.kpi.sc.parser.ext.MyException;
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

public class ReduceRulesMapping {
	private static final Map<Class<?>, String> classToRepresentationMap = new HashMap<Class<?>, String>() {
		{
			put(ASingleCompilationUnit.class, "compilation_unit = public_class");
			put(AMultiCompilationUnit.class,
					"compilation_unit = public_class class_seq");
			put(ASingleClassSeq.class, "class_seq = not_public_class");
			put(AMultiClassSeq.class, "class_seq = not_public_class class_seq");
			put(APublicClass.class,
					"public_class = public TClassToken identifier TLBrc class_body TRBrc");
			put(ANotPublicClass.class,
					"not_public_class = TClassToken identifier TLBrc class_body TRBrc");
			put(AClassBody.class, "class_body = class_body_elem");
			put(AFunctionClassBodyElem.class,
					"class_body_elem = function_definition");
			put(AVariableClassBodyElem.class,
					"class_body_elem = variable_definition TSemi");
			put(AConstantClassBodyElem.class,
					"class_body_elem = constant_definition TSemi");
			put(AFunctionDefinition.class,
					"function_definition = function_declaration function_body");
			put(AVariableDefinition.class,
					"variable_definition = variable_type variable_name");
			put(AConstantDefinition.class,
					"constant_definition = TFinal variable_type variable_name TAssign literal");
			put(AStringLiteral.class, "literal = literal_string");
			put(ABooleanLiteral.class, "literal = literal_boolean");
			put(ANumericLiteral.class, "literal = literal_numeric");
			put(ANullLiteral.class, "literal = literal_null");
			put(ALiteralString.class, "TDoubleQuote char* double_quote_closing");
			put(ADoubleQuoteClosing.class,
					"double_quote_closing = TDoubleQuote");
			put(AIntLiteralNumeric.class, "literal_numeric = integer");
			put(AFracLiteralNumeric.class,
					"literal_numeric = TDot non_negative_integer");
			put(ARealLiteralNumeric.class,
					"literal_numeric = integer TDot non_negative_integer");
			put(ASimpleInteger.class, "integer = non_negative_integer");
			put(APositiveSignedInteger.class,
					"integer = TPlus non_negative_integer");
			put(ANegativeInteger.class, "integer = minus non_negative_integer");
			put(AVariableName.class, "variable_name = TIdentifier");
			put(AFunctionName.class, "variable_name = TIdentifier");
			put(
					AFunctionDeclaration.class,
					"function_declaration = TPublic result_type function_name TLPar formal_arg_list TRPar");
			put(AVoidResultType.class, "result_type = TVoid");
			put(AVariableResultType.class, "result_type = variable_type");
			put(AScalarVariableType.class, "variable_type = type");
			put(AArrayVariableType.class, "variable_type = type TLBkt TRBkt");
			put(ABooleanType.class, "type = TBoolean");
			put(AIntType.class, "type = TInt");
			put(AStringType.class, "type = TStringToken");
			put(ATypeType.class, "type = TIdentifier");
			put(ATypeName.class, "type_name = type_name");
			put(ANothingFormalArgList.class, "formal_arg_list = ");
			put(ASingleVarFormalArgList.class,
					"formal_arg_list = variable_definition TComma formal_arg_list");
			put(AMultipleNVarFormalArgList.class,
					"formal_arg_list = variable_definition");
			put(AMethodName.class, "method_name = identifier");
			put(AMethodName.class, "method_name = identifier");
			put(AConstantName.class, "constant_name = identifier");
			put(ANormalFunctionBody.class,
					"function_body = TLBrc block return expression TSemi TRBrc");
			put(AVoidFunctionBody.class,
					"function_body = TLBrc block return TSemi TRBrc");
			put(ASingleBlock.class, "block = operator*");
			put(ASimpleOperator.class, "operator = expression TSemi");
			put(AAssignOperator.class,
					"operator = variable_name TAssign expression TSemi");
			put(ACondOperator.class, "operator = conditional_operator");
			put(ACycleCycleOperator.class, "operator = cycle_operator");
			put(ASimpleIf.class,
					"simple_if = TIf TLPar expression TRPar TLBrc block TRBrc");
			put(AElseConditionalOperator.class,
					"simple_if else TLBrc block TRBrc");
			put(ASimpleConditionalOperator.class,
					"conditional_operator = simple_if TElse TLBrc block TRBrc");
			put(ACycleOperator.class,
					"cycle_operator = while TLPar expression TRPar TLBrc block TRBrc");
			put(ASimpleExpression.class, "expression = operand_or");
			put(AOrExprExpression.class,
					"expression = operand_or TBarBar expression");
			put(ASimpleOperandOr.class, "operand_or = operand_and");
			put(AAndOperandOr.class,
					"operand_or = operand_and TAmpAmp operand_or");
			put(ASimpleOperandAnd.class, "operand_and = comparison_expression");
			put(AEqOperandAnd.class,
					"operand_and = comparison_expression TEq operand_and");
			put(ANeqOperandAnd.class,
					"operand_and = comparison_expression TNeq operand_and");
			put(ASimpleComparisonExpression.class,
					"comparison_expression = simple_expression");
			put(AGtComparisonExpression.class,
					"comparison_expression = simple_expression TGt comparison_expression");
			put(ALtComparisonExpression.class,
					"comparison_expression = simple_expression TLt comparison_expression");
			put(ALteqComparisonExpression.class,
					"comparison_expression = simple_expression TLteq comparison_expression");
			put(AGteqComparisonExpression.class,
					"comparison_expression = simple_expression TGteq comparison_expression");

			put(ASimpleSimpleExpression.class, "simple_expression = summand");
			put(AAddSimpleExpression.class,
					"simple_expression = summand TPlus simple_expression");
			put(ASubSimpleExpression.class,
					"simple_expression = summand TMinus simple_expression");

			put(ASimpleSummand.class, "summand = multiplier");
			put(AMulSummand.class, "summand = multiplier TStar summand");
			put(ADivSummand.class, "summand = multiplier TSlash summand");
			put(ARemSummand.class, "summand = multiplier TPercent summand");
			put(ASimpleMultiplier.class, "multiplier = cast");
			put(ANegMultiplier.class, "multiplier = TEmark multiplier");
			put(ASimpleCast.class, "elemental_expression");
			put(ANegCast.class, "TLPar variable_type TRPar cast");
			put(ARecursiveElementalExpression.class,
					"elemental_expression = TIdentifier TDot elemental_expression");
			put(AArrElemElementalExpression.class,
					"elemental_expression = TIdentifier TLBkt expression TRBkt");
			put(ACallElementalExpression.class,
					"elemental_expression = TIdentifier TLPar fact_arg_list TRPar");
			put(ALiteralElementalExpression.class,
					"elemental_expression = literal");
			put(AIdentifierElementalExpression.class,
					"elemental_expression =  TIdentifier");
			put(ASingleVarFormalArgList.class, "expression");
			put(AMultipleNVarFormalArgList.class,
					"expression comma fact_arg_list");
			put(ASingleFactArgList.class, "fact_arg_list = expression");
			put(ASingleFactArgList.class, "fact_arg_list = expression TComma fact_arg_list");
		}
	};

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
		if (result.contains("class_body_elem = constant_definition TSemi")) {
			int i=0;
		}
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
