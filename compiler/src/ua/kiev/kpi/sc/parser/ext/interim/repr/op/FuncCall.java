package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.id.FuncSymbol;
import ua.kiev.kpi.sc.parser.ext.id.FuncType;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.ScopeAware;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Literal;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Evaluator;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Pair;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;

class FuncCall extends Operation implements Evaluator, ScopeAware {
	private final String repr;
	private Scope scope;
	public FuncCall(String repr) {
		super();
		this.repr = repr;
	}

	public TypeSymbol validate(Deque<Pair> typeStack, Translation next) {
		// num args need no type checking
		Literal num = (Literal) typeStack.pop().value;
		if (num.getType() != TypeSymbol.T_INT) {
			throw new MyException("Int literal expected");
		}
		
		int number = Integer.valueOf(num.toString());

		FuncType funcType = (FuncType) typeStack.pop().type;
		
		LinkedList<TypeSymbol> args = new LinkedList<TypeSymbol>();
		for (int i=0; i<number; i++) {
			args.push(typeStack.pop().type);
		}					
		
		TypeSymbol varClass = typeStack.pop().type;
		Scope classScope = varClass.getScope();
		FuncSymbol sym = classScope.getVisibleFuncSymbol(funcType.getName(), args);
		
		if (sym == null) {
			throw new MyException("Function "+funcType.getName()+" not visible");
		}
		return sym.getReturnType();
	}
	
	@Override
	public int getArgsRequired() {
		return -1;
	}

	@Override
	public String toString() {
		return repr;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}
	
}