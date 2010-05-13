package ua.kiev.kpi.sc.parser.ext.scope;

import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Test;

import ua.kiev.kpi.sc.parser.ext.id.FuncSymbol;
import ua.kiev.kpi.sc.parser.ext.id.ScopeException;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.id.VarSymbol;

import com.google.common.collect.Lists;

public class ScopeTest {
	@Test
	public void testParentAndChildScopesHaveSameName()
	{
		Scope parentScope = new RootScope();
		VarSymbol parentVar = new VarSymbol();
		parentVar.setName("a");
		parentScope.addIdentifier(parentVar);
		Scope childScope = new Scope(parentScope);
		VarSymbol childVar = new VarSymbol();
		childVar.setName("a");
		childScope.addIdentifier(childVar);
		Assert.assertEquals(childVar, childScope.getVisibleVarSymbol("a"));
		Assert.assertEquals(parentVar, parentScope.getVisibleVarSymbol("a"));
	}
	
	@Test(expected=ScopeException.class)
	public void testVarRedefinitionInSameScope()
	{
		Scope parentScope = new RootScope();
		VarSymbol firstVar = new VarSymbol();
		firstVar.setName("a");
		parentScope.addIdentifier(firstVar);
		VarSymbol secondVar = new VarSymbol();
		secondVar.setName("a");
		parentScope.addIdentifier(secondVar);
	}
	
	@Test(expected=ScopeException.class)
	// duplicates by java spec
	public void testSameArgsRestricted()
	{
		Scope parentScope = new RootScope();
		TypeSymbol returnType = new TypeSymbol();
		FuncSymbol func1 = new FuncSymbol();
		func1.setReturnType(returnType);
		func1.setName("a");
		func1.setParams(new LinkedList<VarSymbol>());
		parentScope.addIdentifier(func1);
		FuncSymbol func2 = new FuncSymbol();
		func2.setName("a");
		func2.setParams(new LinkedList<VarSymbol>());
		func2.setReturnType(returnType);
		parentScope.addIdentifier(func2);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testDifferentArgsAllowed()
	{
		Scope parentScope = new RootScope();
		TypeSymbol returnType = new TypeSymbol();
		FuncSymbol func1 = new FuncSymbol();
		func1.setReturnType(returnType);
		func1.setName("a");
		func1.setParams(new LinkedList<VarSymbol>());
		parentScope.addIdentifier(func1);
		FuncSymbol func2 = new FuncSymbol();
		func2.setName("a");
		func2.setParams(new LinkedList<VarSymbol>(){
			{
				add(new VarSymbol());
			}
		});
		func2.setReturnType(returnType);
		parentScope.addIdentifier(func2);
	}
	
	@Test
	public void testVarAndFuncHaveSimilarNames()
	{
		Scope parentScope = new RootScope();
		VarSymbol firstVar = new VarSymbol();
		firstVar.setName("a");
		parentScope.addIdentifier(firstVar);
		FuncSymbol func = new FuncSymbol();
		func.setName("a");
		parentScope.addIdentifier(func);
	}
}

