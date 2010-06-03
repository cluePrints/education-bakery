package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import java.util.Deque;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Evaluator;

class WideningOp extends Operation implements Evaluator {
	protected final int args;
	protected final String repr;
	protected TypeSymbol widerType = TypeSymbol.T_FLOAT;
	protected TypeSymbol narrowerType = TypeSymbol.T_INT;
	
	WideningOp(int args, String repr) {
		super();
		this.args = args;
		this.repr = repr;
	}

	public TypeSymbol validate(Deque<TypeSymbol> stack, Translation next) {
		TypeSymbol first = stack.pop();
		TypeSymbol second = stack.pop();
		checkType(first);
		checkType(second);		
		return transformToResult(first, second);
	}

	protected TypeSymbol transformToResult(TypeSymbol first, TypeSymbol second) {
		if (first == widerType || second == widerType) {
			return widerType;
		} else {
			
			return narrowerType;
		}
	}

	private void checkType(TypeSymbol first) {
		if (first != TypeSymbol.T_FLOAT && first != TypeSymbol.T_INT) {
			throw new MyException(
					"Operation is defined only for float and int types");
		}
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