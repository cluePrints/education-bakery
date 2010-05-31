package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import java.util.Deque;
import java.util.Iterator;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Evaluator;

class WideningOp extends Operation implements Evaluator{
		private final int args;
		private final String repr;
		
		WideningOp(int args, String repr) {
			super();
			this.args = args;
			this.repr = repr;
		}
		
		public TypeSymbol validate(Deque<TypeSymbol> stack, Translation next) {
			Iterator<TypeSymbol> it = stack.iterator();
			TypeSymbol first = stack.pop();
			TypeSymbol second = stack.pop();
			checkType(first);
			checkType(second);
			if (first == TypeSymbol.T_FLOAT || second == TypeSymbol.T_FLOAT) {
				return TypeSymbol.T_FLOAT;
			} else {
				return TypeSymbol.T_INT;
			}
		}

		private void checkType(TypeSymbol first) {
			if (first != TypeSymbol.T_FLOAT && first != TypeSymbol.T_INT) {
				throw new MyException("Addition operation is defined only for float and int types");
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