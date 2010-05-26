package ua.kiev.kpi.sc.parser.etc;

import java.util.Collection;

import org.junit.Test;

import ua.kiev.kpi.sc.parser.node.Node;
import ua.kiev.kpi.sc.parser.util.PackageLookup;

import com.google.common.collect.Sets;

public class TerminalsFromClasses {
	@Test
	public void doThat() throws Exception
	{
		PackageLookup l = new PackageLookup();
		Iterable<Class<? extends Node>> classes = l.getClasses("ua.kiev.kpi.sc.parser.node", Node.class);
		Collection<String> results = Sets.newTreeSet();
		for (Class<?> clazz : classes) {
			String name = clazz.getSimpleName();
			if (name.startsWith("T")) {
				name = name.substring(1).toLowerCase();
				results.add(name);
			}
		}
		for (String s : results) {
			System.out.println(s);
		}
		System.out.print(results.size());
	}
}
