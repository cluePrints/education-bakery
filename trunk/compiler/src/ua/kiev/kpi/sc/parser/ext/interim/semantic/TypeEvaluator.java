package ua.kiev.kpi.sc.parser.ext.interim.semantic;

import java.util.Deque;
import java.util.Iterator;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.InvisibleTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.repr.Literal;

import com.google.common.collect.Lists;

public class TypeEvaluator{
	private Deque<TypeSymbol> stack;

	public TypeSymbol evaluate(Deque<Translation> polizStack)
	{
		stack = Lists.newLinkedList();
		Iterator<Translation> it = polizStack.descendingIterator();
		while (it.hasNext()) {
			validate(it.next());
		}
		
		if (stack.size() == 1) {
			return stack.pop();
		} else {
			throw new MyException("Not possible to fold result to single value");
		}
	}
	
	private void validate(Translation next) {
		if (next instanceof InvisibleTranslation) {
			// do nothing
		} else if (next instanceof Evaluator){
			Evaluator e = (Evaluator) next;
			stack.push(e.validate(stack, next));
			
		} else if (next instanceof Literal) {
			Literal l = (Literal) next;
			stack.push(l.getType());
		}
	}
}
