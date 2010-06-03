package ua.kiev.kpi.sc.parser.ext.interim.semantic;

import java.util.Deque;
import java.util.Iterator;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.id.FuncType;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.id.VarSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.repr.FuncPointer;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Literal;
import ua.kiev.kpi.sc.parser.ext.interim.repr.VariablePointer;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;

import com.google.common.collect.Lists;

public class TypeEvaluator
{	

	public void evaluate(Deque<Translation> polizStack)
	{
		Deque<Pair> stack = Lists.newLinkedList();
		Iterator<Translation> it = polizStack.descendingIterator();
		Translation c = it.next();
		
		
		while (it.hasNext()) {
			evaluatePart(it, c, stack);
		}
		
		
	}
	
	
	private Translation evaluatePart(Iterator<Translation> it, Translation cc, Deque<Pair> stack) {
		int expectedSize = stack.size()+1;
		Translation c = moveToStart(it, cc);
		
		while (it.hasNext() && c != Bound.EXPR_END) {
			c = it.next();
			TypeSymbol t = getType(stack, c);
			if (t != null) {
				Pair p = new Pair(t, c);
				stack.push(p);
			}
		}
		
		while (it.hasNext() && c != Bound.EXPR_START) {
			c = it.next();
		}
		
		if (stack.size() == expectedSize) {
			return c;
		} else {
			return c;
			//throw new MyException("Not possible to fold result to single value");
		}
	}

	private Translation moveToStart(Iterator<Translation> it, Translation c) {
		while (it.hasNext() && (c != Bound.EXPR_START)) {
			c = it.next();
		}
		return c;
	}
	
	private TypeSymbol getType(Deque<Pair> stack, Translation next) {
		TypeSymbol result = null;
		if (next instanceof Evaluator){
			Evaluator e = (Evaluator) next;
			result = e.validate(stack, next);
			
		} else if (next instanceof Literal) {
			Literal l = (Literal) next;
			result = l.getType();
			
		} else if (next instanceof VariablePointer) {
			VariablePointer p = (VariablePointer) next;
			Scope scope = p.getScope();
			String varName = p.getName();
			VarSymbol var = scope.getVisibleVarSymbol(varName);
			if (var == null) {
				throw new MyException("Variable "+varName+" not visible.");
			}
			TypeSymbol type = scope.getClassSymbol(var.getType());
			result = type;
		} else if (next instanceof FuncPointer) {
			result = new FuncType((FuncPointer) next); 
		}
		return result;
	}
}
