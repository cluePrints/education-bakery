package types;

import org.junit.Test;

import util.AbstractTest;

public class TypeCases extends AbstractTest{
	@Test
	public void okBasicTest() throws Throwable
	{
		load("okReturnType");
		assertOk();
	}
	
	@Test
	public void failAssignDirectType() throws Throwable
	{
		load("failDirectAssignType");
		assertErrMsg();
	}
	
	@Test
	public void failAddOpTypes() throws Throwable
	{
		load("failAddOpTypes");
		
		assertErrMsg("float int");
	}
}


