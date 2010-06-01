package scope;

import junit.framework.Assert;

import org.junit.Test;

import ua.kiev.kpi.sc.parser.ext.interim.walker.InterimRepresentationBuilder;
import util.AbstractTest;

public class ScopeHarness extends AbstractTest{
	@Test
	public void okBasicTest() throws Throwable
	{
		load("okBasicTest");
		Assert.assertTrue(caught == null);
		
		InterimRepresentationBuilder w = new InterimRepresentationBuilder();
		syntaxTree.apply(w);
	}
	
	@Test
	public void failWrongSignatureCallOnObj() throws Throwable
	{
		load("failWrongSignatureCallOnObj");
		assertErrMsg("Function");
	}
	
	@Test
	public void failWrongSignatureCallOnThis() throws Throwable
	{
		load("failWrongSignatureCallOnThis");
		assertErrMsg();
	}
	
	@Test
	public void failNoFuncDef() throws Throwable
	{
		load("failNoFuncDef");
		assertErrMsg("someFunctionn class NotPublicTest not found");
	}
	
	@Test
	public void failVoidFuncAssign() throws Throwable
	{
		load("failVoidFuncAssign");
		assertErrMsg();
	}
	
	@Test
	public void failVarNotVIsible() throws Throwable
	{
		load("failVarNotVIsible");
		assertErrMsg();
	}
}


