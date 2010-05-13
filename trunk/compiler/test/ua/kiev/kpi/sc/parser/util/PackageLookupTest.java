package ua.kiev.kpi.sc.parser.util;

import junit.framework.Assert;

import org.junit.Test;

public class PackageLookupTest {
	@Test
	public void test() throws ClassNotFoundException
	{
		PackageLookup lookup = new PackageLookup();
		Iterable<?> classes = lookup.getClasses(getClass().getPackage().getName(), getClass());
		Assert.assertTrue(classes.iterator().hasNext());
	}
}
