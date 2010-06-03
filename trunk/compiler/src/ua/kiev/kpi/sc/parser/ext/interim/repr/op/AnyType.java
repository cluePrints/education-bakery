package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import java.util.Deque;

import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Evaluator;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Pair;

class AnyType extends Operation implements Evaluator {
	private final int args;
	private final String repr;
	private final TypeSymbol type;
	

	public AnyType(int args, String repr, TypeSymbol resultType) {
		super();
		this.args = args;
		this.repr = repr;
		this.type = resultType;
	}

	public TypeSymbol validate(Deque<Pair> stack, Translation next) {
		for (int i=0; i<args; i++) {
			stack.pop();
		}
		return type;
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