package ua.kiev.kpi.sc.parser.ext.interim.semantic;

import java.util.Deque;

import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;

public interface Evaluator {
	// the idea is to throw exception if some problem encountered
	// stack is result of previous computations
	// all the validated elements should be removed from stack
	TypeSymbol validate(Deque<TypeSymbol> stack, Translation next);
}
