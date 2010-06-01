package ua.kiev.kpi.sc.parser.analysis;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import com.google.common.collect.Lists;

import ua.kiev.kpi.sc.parser.node.*;

public class AnalysisDelegator implements Analysis {
	protected List<Analysis> analysis = Lists.newLinkedList();

	public AnalysisDelegator(Analysis... analysis) {
		super();
		this.analysis = Arrays.asList(analysis);
	}
	
	public AnalysisDelegator() {
		
	}

	private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }
	
	public void caseStart(Start node) {
		for (Analysis a : analysis) {
			a.caseStart(node);
		}
	}

	public void caseASingleCompilationUnit(ASingleCompilationUnit node) {
		for (Analysis a : analysis) {
			a.caseASingleCompilationUnit(node);
		}
	}

	public void caseAMultiCompilationUnit(AMultiCompilationUnit node) {
		for (Analysis a : analysis) {
			a.caseAMultiCompilationUnit(node);
		}
	}

	public void caseASingleClassSeq(ASingleClassSeq node) {
		for (Analysis a : analysis) {
			a.caseASingleClassSeq(node);
		}
	}

	public void caseAMultiClassSeq(AMultiClassSeq node) {
		for (Analysis a : analysis) {
			a.caseAMultiClassSeq(node);
		}
	}

	public void caseAPublicClass(APublicClass node) {
		for (Analysis a : analysis) {
			a.caseAPublicClass(node);
		}
	}

	public void caseANotPublicClass(ANotPublicClass node) {
		for (Analysis a : analysis) {
			a.caseANotPublicClass(node);
		}
	}

	public void caseAClassBody(AClassBody node) {
		for (Analysis a : analysis) {
			a.caseAClassBody(node);
		}
	}

	public void caseAFunctionClassBodyElem(AFunctionClassBodyElem node) {
		for (Analysis a : analysis) {
			a.caseAFunctionClassBodyElem(node);
		}
	}

	public void caseAVariableClassBodyElem(AVariableClassBodyElem node) {
		for (Analysis a : analysis) {
			a.caseAVariableClassBodyElem(node);
		}
	}

	public void caseAConstantClassBodyElem(AConstantClassBodyElem node) {
		for (Analysis a : analysis) {
			a.caseAConstantClassBodyElem(node);
		}
	}

	public void caseAFunctionDefinition(AFunctionDefinition node) {
		for (Analysis a : analysis) {
			a.caseAFunctionDefinition(node);
		}
	}

	public void caseAVariableDefinition(AVariableDefinition node) {
		for (Analysis a : analysis) {
			a.caseAVariableDefinition(node);
		}
	}

	public void caseAConstantDefinition(AConstantDefinition node) {
		for (Analysis a : analysis) {
			a.caseAConstantDefinition(node);
		}
	}

	public void caseAStringLiteral(AStringLiteral node) {
		for (Analysis a : analysis) {
			a.caseAStringLiteral(node);
		}
	}

	public void caseABooleanLiteral(ABooleanLiteral node) {
		for (Analysis a : analysis) {
			a.caseABooleanLiteral(node);
		}
	}

	public void caseANumericLiteral(ANumericLiteral node) {
		for (Analysis a : analysis) {
			a.caseANumericLiteral(node);
		}
	}

	public void caseANullLiteral(ANullLiteral node) {
		for (Analysis a : analysis) {
			a.caseANullLiteral(node);
		}
	}

	public void caseALiteralString(ALiteralString node) {
		for (Analysis a : analysis) {
			a.caseALiteralString(node);
		}
	}

	public void caseADoubleQuoteClosing(ADoubleQuoteClosing node) {
		for (Analysis a : analysis) {
			a.caseADoubleQuoteClosing(node);
		}
	}

	public void caseAIntLiteralNumeric(AIntLiteralNumeric node) {
		for (Analysis a : analysis) {
			a.caseAIntLiteralNumeric(node);
		}
	}

	public void caseAFracLiteralNumeric(AFracLiteralNumeric node) {
		for (Analysis a : analysis) {
			a.caseAFracLiteralNumeric(node);
		}
	}

	public void caseARealLiteralNumeric(ARealLiteralNumeric node) {
		for (Analysis a : analysis) {
			a.caseARealLiteralNumeric(node);
		}
	}

	public void caseASimpleInteger(ASimpleInteger node) {
		for (Analysis a : analysis) {
			a.caseASimpleInteger(node);
		}
	}

	public void caseAPositiveSignedInteger(APositiveSignedInteger node) {
		for (Analysis a : analysis) {
			a.caseAPositiveSignedInteger(node);
		}
	}

	public void caseANegativeInteger(ANegativeInteger node) {
		for (Analysis a : analysis) {
			a.caseANegativeInteger(node);
		}
	}

	public void caseAVariableName(AVariableName node) {
		for (Analysis a : analysis) {
			a.caseAVariableName(node);
		}
	}

	public void caseAFunctionName(AFunctionName node) {
		for (Analysis a : analysis) {
			a.caseAFunctionName(node);
		}
	}

	public void caseAFunctionDeclaration(AFunctionDeclaration node) {
		for (Analysis a : analysis) {
			a.caseAFunctionDeclaration(node);
		}
	}

	public void caseAVoidResultType(AVoidResultType node) {
		for (Analysis a : analysis) {
			a.caseAVoidResultType(node);
		}
	}

	public void caseAVariableResultType(AVariableResultType node) {
		for (Analysis a : analysis) {
			a.caseAVariableResultType(node);
		}
	}

	public void caseAScalarVariableType(AScalarVariableType node) {
		for (Analysis a : analysis) {
			a.caseAScalarVariableType(node);
		}
	}

	public void caseAArrayVariableType(AArrayVariableType node) {
		for (Analysis a : analysis) {
			a.caseAArrayVariableType(node);
		}
	}

	public void caseABooleanType(ABooleanType node) {
		for (Analysis a : analysis) {
			a.caseABooleanType(node);
		}
	}

	public void caseAIntType(AIntType node) {
		for (Analysis a : analysis) {
			a.caseAIntType(node);
		}
	}

	public void caseAStringType(AStringType node) {
		for (Analysis a : analysis) {
			a.caseAStringType(node);
		}
	}

	public void caseATypeType(ATypeType node) {
		for (Analysis a : analysis) {
			a.caseATypeType(node);
		}
	}

	public void caseATypeName(ATypeName node) {
		for (Analysis a : analysis) {
			a.caseATypeName(node);
		}
	}

	public void caseANothingFormalArgList(ANothingFormalArgList node) {
		for (Analysis a : analysis) {
			a.caseANothingFormalArgList(node);
		}
	}

	public void caseASingleVarFormalArgList(ASingleVarFormalArgList node) {
		for (Analysis a : analysis) {
			a.caseASingleVarFormalArgList(node);
		}
	}

	public void caseAMultipleNVarFormalArgList(AMultipleNVarFormalArgList node) {
		for (Analysis a : analysis) {
			a.caseAMultipleNVarFormalArgList(node);
		}
	}

	public void caseAMethodName(AMethodName node) {
		for (Analysis a : analysis) {
			a.caseAMethodName(node);
		}
	}

	public void caseAConstantName(AConstantName node) {
		for (Analysis a : analysis) {
			a.caseAConstantName(node);
		}
	}

	public void caseANormalFunctionBody(ANormalFunctionBody node) {
		for (Analysis a : analysis) {
			a.caseANormalFunctionBody(node);
		}
	}

	public void caseAVoidFunctionBody(AVoidFunctionBody node) {
		for (Analysis a : analysis) {
			a.caseAVoidFunctionBody(node);
		}
	}

	public void caseASingleBlock(ASingleBlock node) {
		for (Analysis a : analysis) {
			a.caseASingleBlock(node);
		}
	}

	public void caseASimpleOperator(ASimpleOperator node) {
		for (Analysis a : analysis) {
			a.caseASimpleOperator(node);
		}
	}

	public void caseAAssignOperator(AAssignOperator node) {
		for (Analysis a : analysis) {
			a.caseAAssignOperator(node);
		}
	}

	public void caseACondOperator(ACondOperator node) {
		for (Analysis a : analysis) {
			a.caseACondOperator(node);
		}
	}

	public void caseACycleOperator(ACycleOperator node) {
		for (Analysis a : analysis) {
			a.caseACycleOperator(node);
		}
	}

	public void caseASimpleIf(ASimpleIf node) {
		for (Analysis a : analysis) {
			a.caseASimpleIf(node);
		}
	}

	public void caseASimpleConditionalOperator(ASimpleConditionalOperator node) {
		for (Analysis a : analysis) {
			a.caseASimpleConditionalOperator(node);
		}
	}

	public void caseAElseConditionalOperator(AElseConditionalOperator node) {
		for (Analysis a : analysis) {
			a.caseAElseConditionalOperator(node);
		}
	}

	public void caseACycleCycleOperator(ACycleCycleOperator node) {
		for (Analysis a : analysis) {
			a.caseACycleCycleOperator(node);
		}
	}

	public void caseASimpleExpression(ASimpleExpression node) {
		for (Analysis a : analysis) {
			a.caseASimpleExpression(node);
		}
	}

	public void caseAOrExprExpression(AOrExprExpression node) {
		for (Analysis a : analysis) {
			a.caseAOrExprExpression(node);
		}
	}

	public void caseASimpleOperandOr(ASimpleOperandOr node) {
		for (Analysis a : analysis) {
			a.caseASimpleOperandOr(node);
		}
	}

	public void caseAAndOperandOr(AAndOperandOr node) {
		for (Analysis a : analysis) {
			a.caseAAndOperandOr(node);
		}
	}

	public void caseASimpleOperandAnd(ASimpleOperandAnd node) {
		for (Analysis a : analysis) {
			a.caseASimpleOperandAnd(node);
		}
	}

	public void caseAEqOperandAnd(AEqOperandAnd node) {
		for (Analysis a : analysis) {
			a.caseAEqOperandAnd(node);
		}
	}

	public void caseANeqOperandAnd(ANeqOperandAnd node) {
		for (Analysis a : analysis) {
			a.caseANeqOperandAnd(node);
		}
	}

	public void caseASimpleComparisonExpression(ASimpleComparisonExpression node) {
		for (Analysis a : analysis) {
			a.caseASimpleComparisonExpression(node);
		}
	}

	public void caseAGtComparisonExpression(AGtComparisonExpression node) {
		for (Analysis a : analysis) {
			a.caseAGtComparisonExpression(node);
		}
	}

	public void caseALtComparisonExpression(ALtComparisonExpression node) {
		for (Analysis a : analysis) {
			a.caseALtComparisonExpression(node);
		}
	}

	public void caseALteqComparisonExpression(ALteqComparisonExpression node) {
		for (Analysis a : analysis) {
			a.caseALteqComparisonExpression(node);
		}
	}

	public void caseAGteqComparisonExpression(AGteqComparisonExpression node) {
		for (Analysis a : analysis) {
			a.caseAGteqComparisonExpression(node);
		}
	}

	public void caseASimpleSimpleExpression(ASimpleSimpleExpression node) {
		for (Analysis a : analysis) {
			a.caseASimpleSimpleExpression(node);
		}
	}

	public void caseAAddSimpleExpression(AAddSimpleExpression node) {
		for (Analysis a : analysis) {
			a.caseAAddSimpleExpression(node);
		}
	}

	public void caseASubSimpleExpression(ASubSimpleExpression node) {
		for (Analysis a : analysis) {
			a.caseASubSimpleExpression(node);
		}
	}

	public void caseASimpleSummand(ASimpleSummand node) {
		for (Analysis a : analysis) {
			a.caseASimpleSummand(node);
		}
	}

	public void caseAMulSummand(AMulSummand node) {
		for (Analysis a : analysis) {
			a.caseAMulSummand(node);
		}
	}

	public void caseADivSummand(ADivSummand node) {
		for (Analysis a : analysis) {
			a.caseADivSummand(node);
		}
	}

	public void caseARemSummand(ARemSummand node) {
		for (Analysis a : analysis) {
			a.caseARemSummand(node);
		}
	}

	public void caseASimpleMultiplier(ASimpleMultiplier node) {
		for (Analysis a : analysis) {
			a.caseASimpleMultiplier(node);
		}
	}

	public void caseANegMultiplier(ANegMultiplier node) {
		for (Analysis a : analysis) {
			a.caseANegMultiplier(node);
		}
	}

	public void caseASimpleCast(ASimpleCast node) {
		for (Analysis a : analysis) {
			a.caseASimpleCast(node);
		}
	}

	public void caseANegCast(ANegCast node) {
		for (Analysis a : analysis) {
			a.caseANegCast(node);
		}
	}

	public void caseARecursiveElementalExpression(
			ARecursiveElementalExpression node) {
		for (Analysis a : analysis) {
			a.caseARecursiveElementalExpression(node);
		}
	}

	public void caseAArrElemElementalExpression(AArrElemElementalExpression node) {
		for (Analysis a : analysis) {
			a.caseAArrElemElementalExpression(node);
		}
	}

	public void caseACallElementalExpression(ACallElementalExpression node) {
		for (Analysis a : analysis) {
			a.caseACallElementalExpression(node);
		}
	}

	public void caseALiteralElementalExpression(ALiteralElementalExpression node) {
		for (Analysis a : analysis) {
			a.caseALiteralElementalExpression(node);
		}
	}

	public void caseAIdentifierElementalExpression(
			AIdentifierElementalExpression node) {
		for (Analysis a : analysis) {
			a.caseAIdentifierElementalExpression(node);
		}
	}

	public void caseASingleFactArgList(ASingleFactArgList node) {
		for (Analysis a : analysis) {
			a.caseASingleFactArgList(node);
		}
	}

	public void caseAMultipleFactArgList(AMultipleFactArgList node) {
		for (Analysis a : analysis) {
			a.caseAMultipleFactArgList(node);
		}
	}

	public void caseTComma(TComma node) {
		for (Analysis a : analysis) {
			a.caseTComma(node);
		}
	}

	public void caseTDot(TDot node) {
		for (Analysis a : analysis) {
			a.caseTDot(node);
		}
	}

	public void caseTLBkt(TLBkt node) {
		for (Analysis a : analysis) {
			a.caseTLBkt(node);
		}
	}

	public void caseTLBrc(TLBrc node) {
		for (Analysis a : analysis) {
			a.caseTLBrc(node);
		}
	}

	public void caseTLPar(TLPar node) {
		for (Analysis a : analysis) {
			a.caseTLPar(node);
		}
	}

	public void caseTRBkt(TRBkt node) {
		for (Analysis a : analysis) {
			a.caseTRBkt(node);
		}
	}

	public void caseTRBrc(TRBrc node) {
		for (Analysis a : analysis) {
			a.caseTRBrc(node);
		}
	}

	public void caseTRPar(TRPar node) {
		for (Analysis a : analysis) {
			a.caseTRPar(node);
		}
	}

	public void caseTSemi(TSemi node) {
		for (Analysis a : analysis) {
			a.caseTSemi(node);
		}
	}

	public void caseTDoubleQuote(TDoubleQuote node) {
		for (Analysis a : analysis) {
			a.caseTDoubleQuote(node);
		}
	}

	public void caseTAmpAmp(TAmpAmp node) {
		for (Analysis a : analysis) {
			a.caseTAmpAmp(node);
		}
	}

	public void caseTAssign(TAssign node) {
		for (Analysis a : analysis) {
			a.caseTAssign(node);
		}
	}

	public void caseTBarBar(TBarBar node) {
		for (Analysis a : analysis) {
			a.caseTBarBar(node);
		}
	}

	public void caseTEmark(TEmark node) {
		for (Analysis a : analysis) {
			a.caseTEmark(node);
		}
	}

	public void caseTEq(TEq node) {
		for (Analysis a : analysis) {
			a.caseTEq(node);
		}
	}

	public void caseTGt(TGt node) {
		for (Analysis a : analysis) {
			a.caseTGt(node);
		}
	}

	public void caseTGteq(TGteq node) {
		for (Analysis a : analysis) {
			a.caseTGteq(node);
		}
	}

	public void caseTLt(TLt node) {
		for (Analysis a : analysis) {
			a.caseTLt(node);
		}
	}

	public void caseTLteq(TLteq node) {
		for (Analysis a : analysis) {
			a.caseTLteq(node);
		}
	}

	public void caseTMinus(TMinus node) {
		for (Analysis a : analysis) {
			a.caseTMinus(node);
		}
	}

	public void caseTNeq(TNeq node) {
		for (Analysis a : analysis) {
			a.caseTNeq(node);
		}
	}

	public void caseTPercent(TPercent node) {
		for (Analysis a : analysis) {
			a.caseTPercent(node);
		}
	}

	public void caseTPlus(TPlus node) {
		for (Analysis a : analysis) {
			a.caseTPlus(node);
		}
	}

	public void caseTSlash(TSlash node) {
		for (Analysis a : analysis) {
			a.caseTSlash(node);
		}
	}

	public void caseTStar(TStar node) {
		for (Analysis a : analysis) {
			a.caseTStar(node);
		}
	}

	public void caseTBoolean(TBoolean node) {
		for (Analysis a : analysis) {
			a.caseTBoolean(node);
		}
	}

	public void caseTClassToken(TClassToken node) {
		for (Analysis a : analysis) {
			a.caseTClassToken(node);
		}
	}

	public void caseTElse(TElse node) {
		for (Analysis a : analysis) {
			a.caseTElse(node);
		}
	}

	public void caseTFinal(TFinal node) {
		for (Analysis a : analysis) {
			a.caseTFinal(node);
		}
	}

	public void caseTFloat(TFloat node) {
		for (Analysis a : analysis) {
			a.caseTFloat(node);
		}
	}

	public void caseTFor(TFor node) {
		for (Analysis a : analysis) {
			a.caseTFor(node);
		}
	}

	public void caseTIf(TIf node) {
		for (Analysis a : analysis) {
			a.caseTIf(node);
		}
	}

	public void caseTInt(TInt node) {
		for (Analysis a : analysis) {
			a.caseTInt(node);
		}
	}

	public void caseTNew(TNew node) {
		for (Analysis a : analysis) {
			a.caseTNew(node);
		}
	}

	public void caseTPublic(TPublic node) {
		for (Analysis a : analysis) {
			a.caseTPublic(node);
		}
	}

	public void caseTReturn(TReturn node) {
		for (Analysis a : analysis) {
			a.caseTReturn(node);
		}
	}

	public void caseTShort(TShort node) {
		for (Analysis a : analysis) {
			a.caseTShort(node);
		}
	}

	public void caseTStatic(TStatic node) {
		for (Analysis a : analysis) {
			a.caseTStatic(node);
		}
	}

	public void caseTVoid(TVoid node) {
		for (Analysis a : analysis) {
			a.caseTVoid(node);
		}
	}

	public void caseTWhile(TWhile node) {
		for (Analysis a : analysis) {
			a.caseTWhile(node);
		}
	}

	public void caseTStringToken(TStringToken node) {
		for (Analysis a : analysis) {
			a.caseTStringToken(node);
		}
	}

	public void caseTLiteralBoolean(TLiteralBoolean node) {
		for (Analysis a : analysis) {
			a.caseTLiteralBoolean(node);
		}
	}

	public void caseTLiteralNull(TLiteralNull node) {
		for (Analysis a : analysis) {
			a.caseTLiteralNull(node);
		}
	}

	public void caseTWhitespace(TWhitespace node) {
		for (Analysis a : analysis) {
			a.caseTWhitespace(node);
		}
	}

	public void caseTIdentifier(TIdentifier node) {
		for (Analysis a : analysis) {
			a.caseTIdentifier(node);
		}
	}

	public void caseTNonNegativeInteger(TNonNegativeInteger node) {
		for (Analysis a : analysis) {
			a.caseTNonNegativeInteger(node);
		}
	}

	public void caseTChar(TChar node) {
		for (Analysis a : analysis) {
			a.caseTChar(node);
		}
	}

	public void caseEOF(EOF node) {
		for (Analysis a : analysis) {
			a.caseEOF(node);
		}
	}
}
