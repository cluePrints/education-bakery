package scope;

import junit.framework.Assert;

import org.junit.Test;

import util.AbstractTest;

public class ScopeHarness extends AbstractTest{
	@Test
	public void okBasicTest() throws Throwable
	{
		load("okBasicTest");
		Assert.assertEquals(null, caught);
	}
	
	@Test
	public void failWrongSignatureCallOnObj() throws Throwable
	{
		load("failWrongSignatureCallOnObj");
		assertErrMsg("someFunction");
	}
	
	@Test
	public void failWrongSignatureCallOnThis() throws Throwable
	{
		load("failWrongSignatureCallOnThis");
		assertErrMsg("test");
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
		assertErrMsg("testVoid");
	}
	
	@Test
	public void failVarNotVIsible() throws Throwable
	{
		load("failVarNotVIsible");
		assertErrMsg("notDefined");
	}
}


