package interimgen;

import java.util.Deque;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.repr.op.Operation;
import util.AbstractTest;

public class ScopeHarness extends AbstractTest{
	@Test
	public void okEq() throws Throwable
	{
		load("okEq");
		assertOk();
		
		Deque<Translation> stack = interimBuilder.getFilteredPolizStack();
		Iterator<Translation> it = stack.iterator();
		Translation sym = it.next();
		while (it.hasNext() && !Operation.EQ().equals(sym)) {
			sym = it.next();
		}
		Assert.assertTrue(Operation.EQ().equals(sym));
	}
	
	@Test
	public void okNeq() throws Throwable
	{
		load("okNeq");
		assertOk();
		
		Deque<Translation> stack = interimBuilder.getFilteredPolizStack();
		Iterator<Translation> it = stack.iterator();
		Translation sym = it.next();
		while (it.hasNext() && !Operation.NEGATION().equals(sym)) {
			sym = it.next();
		}
		Assert.assertTrue(Operation.NEGATION().equals(sym));
	}
}


