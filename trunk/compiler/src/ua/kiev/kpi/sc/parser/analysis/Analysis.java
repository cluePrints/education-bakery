/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.analysis;

import ua.kiev.kpi.sc.parser.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseASingleCompilationUnit(ASingleCompilationUnit node);
    void caseAMultiCompilationUnit(AMultiCompilationUnit node);
    void caseASingleClassSeq(ASingleClassSeq node);
    void caseAMultiClassSeq(AMultiClassSeq node);
    void caseAPublicClass(APublicClass node);
    void caseANotPublicClass(ANotPublicClass node);
    void caseAClassBody(AClassBody node);
    void caseAFunctionClassBodyElem(AFunctionClassBodyElem node);
    void caseAVariableClassBodyElem(AVariableClassBodyElem node);
    void caseAConstantClassBodyElem(AConstantClassBodyElem node);
    void caseAFunctionDefinition(AFunctionDefinition node);
    void caseAVariableDefinition(AVariableDefinition node);
    void caseAConstantDefinition(AConstantDefinition node);
    void caseAStringLiteral(AStringLiteral node);
    void caseABooleanLiteral(ABooleanLiteral node);
    void caseANumericLiteral(ANumericLiteral node);
    void caseANullLiteral(ANullLiteral node);
    void caseALiteralString(ALiteralString node);
    void caseADoubleQuoteClosing(ADoubleQuoteClosing node);
    void caseAIntLiteralNumeric(AIntLiteralNumeric node);
    void caseAFracLiteralNumeric(AFracLiteralNumeric node);
    void caseARealLiteralNumeric(ARealLiteralNumeric node);
    void caseASimpleInteger(ASimpleInteger node);
    void caseAPositiveSignedInteger(APositiveSignedInteger node);
    void caseANegativeInteger(ANegativeInteger node);
    void caseAVariableName(AVariableName node);
    void caseAFunctionName(AFunctionName node);
    void caseAFunctionDeclaration(AFunctionDeclaration node);
    void caseAVoidResultType(AVoidResultType node);
    void caseAVariableResultType(AVariableResultType node);
    void caseAScalarVariableType(AScalarVariableType node);
    void caseAArrayVariableType(AArrayVariableType node);
    void caseABooleanType(ABooleanType node);
    void caseAIntType(AIntType node);
    void caseAStringType(AStringType node);
    void caseATypeType(ATypeType node);
    void caseATypeName(ATypeName node);
    void caseANothingFormalArgList(ANothingFormalArgList node);
    void caseASingleVarFormalArgList(ASingleVarFormalArgList node);
    void caseAMultipleNVarFormalArgList(AMultipleNVarFormalArgList node);
    void caseAMethodName(AMethodName node);
    void caseAConstantName(AConstantName node);
    void caseANormalFunctionBody(ANormalFunctionBody node);
    void caseAVoidFunctionBody(AVoidFunctionBody node);
    void caseASingleBlock(ASingleBlock node);
    void caseASimpleOperator(ASimpleOperator node);
    void caseAAssignOperator(AAssignOperator node);
    void caseACondOperator(ACondOperator node);
    void caseACycleOperator(ACycleOperator node);
    void caseASimpleIf(ASimpleIf node);
    void caseASimpleConditionalOperator(ASimpleConditionalOperator node);
    void caseAElseConditionalOperator(AElseConditionalOperator node);
    void caseACycleCycleOperator(ACycleCycleOperator node);
    void caseASimpleExpression(ASimpleExpression node);
    void caseAOrExprExpression(AOrExprExpression node);
    void caseASimpleOperandOr(ASimpleOperandOr node);
    void caseAAndOperandOr(AAndOperandOr node);
    void caseASimpleOperandAnd(ASimpleOperandAnd node);
    void caseAEqOperandAnd(AEqOperandAnd node);
    void caseANeqOperandAnd(ANeqOperandAnd node);
    void caseASimpleComparisonExpression(ASimpleComparisonExpression node);
    void caseAGtComparisonExpression(AGtComparisonExpression node);
    void caseALtComparisonExpression(ALtComparisonExpression node);
    void caseALteqComparisonExpression(ALteqComparisonExpression node);
    void caseAGteqComparisonExpression(AGteqComparisonExpression node);
    void caseASimpleSimpleExpression(ASimpleSimpleExpression node);
    void caseAAddSimpleExpression(AAddSimpleExpression node);
    void caseASubSimpleExpression(ASubSimpleExpression node);
    void caseASimpleSummand(ASimpleSummand node);
    void caseAMulSummand(AMulSummand node);
    void caseADivSummand(ADivSummand node);
    void caseARemSummand(ARemSummand node);
    void caseASimpleMultiplier(ASimpleMultiplier node);
    void caseANegMultiplier(ANegMultiplier node);
    void caseASimpleCast(ASimpleCast node);
    void caseANegCast(ANegCast node);
    void caseARecursiveElementalExpression(ARecursiveElementalExpression node);
    void caseAArrElemElementalExpression(AArrElemElementalExpression node);
    void caseACallElementalExpression(ACallElementalExpression node);
    void caseALiteralElementalExpression(ALiteralElementalExpression node);
    void caseAIdentifierElementalExpression(AIdentifierElementalExpression node);
    void caseASingleFactArgList(ASingleFactArgList node);
    void caseAMultipleFactArgList(AMultipleFactArgList node);

    void caseTComma(TComma node);
    void caseTDot(TDot node);
    void caseTLBkt(TLBkt node);
    void caseTLBrc(TLBrc node);
    void caseTLPar(TLPar node);
    void caseTRBkt(TRBkt node);
    void caseTRBrc(TRBrc node);
    void caseTRPar(TRPar node);
    void caseTSemi(TSemi node);
    void caseTDoubleQuote(TDoubleQuote node);
    void caseTAmpAmp(TAmpAmp node);
    void caseTAssign(TAssign node);
    void caseTBarBar(TBarBar node);
    void caseTEmark(TEmark node);
    void caseTEq(TEq node);
    void caseTGt(TGt node);
    void caseTGteq(TGteq node);
    void caseTLt(TLt node);
    void caseTLteq(TLteq node);
    void caseTMinus(TMinus node);
    void caseTNeq(TNeq node);
    void caseTPercent(TPercent node);
    void caseTPlus(TPlus node);
    void caseTSlash(TSlash node);
    void caseTStar(TStar node);
    void caseTBoolean(TBoolean node);
    void caseTClassToken(TClassToken node);
    void caseTElse(TElse node);
    void caseTFinal(TFinal node);
    void caseTFloat(TFloat node);
    void caseTFor(TFor node);
    void caseTIf(TIf node);
    void caseTInt(TInt node);
    void caseTNew(TNew node);
    void caseTPublic(TPublic node);
    void caseTReturn(TReturn node);
    void caseTShort(TShort node);
    void caseTStatic(TStatic node);
    void caseTVoid(TVoid node);
    void caseTWhile(TWhile node);
    void caseTStringToken(TStringToken node);
    void caseTLiteralBoolean(TLiteralBoolean node);
    void caseTLiteralNull(TLiteralNull node);
    void caseTWhitespace(TWhitespace node);
    void caseTIdentifier(TIdentifier node);
    void caseTNonNegativeInteger(TNonNegativeInteger node);
    void caseTChar(TChar node);
    void caseEOF(EOF node);
}
