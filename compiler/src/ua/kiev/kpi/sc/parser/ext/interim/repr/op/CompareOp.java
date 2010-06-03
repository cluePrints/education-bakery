package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;

public class CompareOp extends WideningOp{
	public CompareOp(String repr) {
		super(2, repr);
	}
	
	@Override
	protected TypeSymbol transformToResult(TypeSymbol first, TypeSymbol second) {
		return TypeSymbol.T_BOOLEAN;
	}
}
