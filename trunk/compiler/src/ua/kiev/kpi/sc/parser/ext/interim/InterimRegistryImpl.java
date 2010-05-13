package ua.kiev.kpi.sc.parser.ext.interim;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.util.PackageLookup;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class InterimRegistryImpl implements InterimsRegistry {
	private PackageLookup lookup = new PackageLookup();
	private Iterable<? extends Interim> cache = null;
	
	public synchronized Iterable<? extends Interim> lookupAll() {
		if (cache == null) {
			cache = lookup();
		}
		return cache;
	}
	
	private Iterable<? extends Interim> lookup() {
		Iterable<Class<? extends Interim>> classes;
		try {
			classes = lookup.getClasses("ua.kiev.kpi.sc.parser.ext.interim.impl", Interim.class);			
		} catch (ClassNotFoundException ex) {
			classes = Lists.newLinkedList();
		}
		Iterable<? extends Interim> result;
		result = Iterables.transform(classes, new Function<Class<? extends Interim>, Interim>() {
			public Interim apply(Class<? extends Interim> from) {	
				try{
					return from.newInstance();
				} catch (InstantiationException ex) {
					throw new MyException(ex);
				} catch (IllegalAccessException ex) {
					throw new MyException(ex);
				}
			}
		});
		return result;
	}

}
