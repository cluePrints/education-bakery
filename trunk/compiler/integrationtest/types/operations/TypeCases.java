package types.operations;

import org.junit.Test;

import util.AbstractTest;

public class TypeCases extends AbstractTest{
	@Test
	public void okReturnType() throws Throwable
	{
		load("okReturnType");
		assertOk();
	}
	
	@Test
	public void okMulTypes() throws Throwable
	{
		load("okMulTypes");
		assertOk();
	}
	
	@Test
	public void okNegBool() throws Throwable
	{
		load("okNegBool");
		assertOk();
	}
	
	@Test
	public void okEqChecksForAll() throws Throwable
	{
		load("okEqChecksForAll");
		assertOk();
	}
	
	@Test
	public void failNegInt() throws Throwable
	{
		load("failNegInt");
		assertErrMsg("boolean");
	}
	
	@Test
	public void failAssignDifferent() throws Throwable
	{
		load("failAssignDifferent");
		assertErrMsg("int");
	}
	
	@Test
	public void okAssignSame() throws Throwable
	{
		load("okAssignSame");
		assertOk();
	}
	
	@Test
	public void failCompareWithObject() throws Throwable
	{
		load("failCompareWithObject");
		assertErrMsg("float int");
	}
	
	@Test
	public void failMulTypes() throws Throwable
	{
		load("failMulTypes");
		assertErrMsg("float int");
	}
	
	@Test
	public void failAssignDirectType() throws Throwable
	{
		load("failDirectAssignType");
		assertErrMsg("int");
	}
	
	@Test
	public void failAddOpTypes() throws Throwable
	{
		load("failAddOpTypes");		
		assertErrMsg("float int");
	}
}


