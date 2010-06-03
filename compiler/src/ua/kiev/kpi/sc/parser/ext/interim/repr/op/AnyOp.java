package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import java.util.Deque;

import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Evaluator;

class AnyOp extends Operation implements Evaluator {
	private final int args;
	private final String repr;
	private final TypeSymbol resultType;
	

	private AnyOp(int args, String repr, TypeSymbol resultType) {
		super();
		this.args = args;
		this.repr = repr;
		this.resultType = resultType;
	}

	public TypeSymbol validate(Deque<TypeSymbol> stack, Translation next) {
		for (int i=0; i<args; i++) {
			stack.pop();
		}
		return resultType;
	}
	
	@Override
	public int getArgsRequired() {
		return args;
	}

	@Override
	public String toString() {
		return repr;
	}
}