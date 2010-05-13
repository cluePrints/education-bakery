package ua.kiev.kpi.sc.parser.ext.interim;

import java.util.Iterator;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.node.Node;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class InterimsFactory {
	private InterimsRegistry registry;

	public Interim create(final int currentRuleNum, final Class<? extends Node> currentNodeFoldedClass)
	{
		Iterable<? extends Interim> interims = registry.lookupAll();
		Iterable<? extends Interim> candidates = Iterables.filter(interims, new Predicate<Interim>() {
			public boolean apply(Interim input) {
				TriggeredFor trig = input.getClass().getAnnotation(TriggeredFor.class);
				for (int ruleId : trig.ruleIdArray()) {
					if (ruleId == currentRuleNum) {
						return true;
					}
					if (currentNodeFoldedClass == null) {
						return false;
					}
					for (Class<? extends Node> clazz : trig.reductedNodesArray()) {
						if (clazz.isAssignableFrom(currentNodeFoldedClass)) {
							;;
							return true;
						}
					}
				}
				return false;
			}
		});
		
		
		Iterator<? extends Interim> candidatesIterator = candidates.iterator();
		Interim result = null;
		if (candidatesIterator.hasNext()) {
			result = candidatesIterator.next();		
			if (candidatesIterator.hasNext()) {
				throw new MyException("We've got multiple candidates for this position");
			}
		}
		return result;
	}
	
	public void setRegistry(InterimsRegistry registry) {
		this.registry = registry;
	}
}
