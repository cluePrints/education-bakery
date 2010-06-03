package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import java.util.Deque;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Evaluator;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Pair;

class SingleType extends Operation implements Evaluator {
	private final int args;
	private final String repr;
	private final TypeSymbol resultType;
	

	public SingleType(int args, String repr, TypeSymbol resultType) {
		super();
		this.args = args;
		this.repr = repr;
		this.resultType = resultType;
	}

	public TypeSymbol validate(Deque<Pair> stack, Translation next) {
		TypeSymbol type = resultType;
		TypeSymbol t = null;
		for (int i=0; i<args; i++) {
			t = stack.pop().type;			
			if (type != null && !t.equals(type)) {
				throw new MyException("Type "+type+" expected.");
			}
			if (type == null) {
				type = t;
			}
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