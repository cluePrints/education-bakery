package ua.kiev.kpi.sc.parser.util;

import junit.framework.Assert;

import org.junit.Test;

public class PackageLookupTest {
	@Test
	public void test() throws ClassNotFoundException
	{
		PackageLookup lookup = new PackageLookup();
		Iterable<Class<?>> classes = lookup.getClasses(getClass().getPackage().getName());
		Assert.assertTrue(classes.iterator().hasNext());
	}
}
