package util;

import java.io.FileReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import ua.kiev.kpi.sc.parser.ext.ScopeTreeBuilder;
import ua.kiev.kpi.sc.parser.ext.ScopeTreeChecker;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Bound;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.Evaluator;
import ua.kiev.kpi.sc.parser.ext.interim.semantic.TypeEvaluator;
import ua.kiev.kpi.sc.parser.ext.interim.walker.InterimRepresentationBuilder;
import ua.kiev.kpi.sc.parser.ext.scope.RootScope;
import ua.kiev.kpi.sc.parser.lexer.Lexer;
import ua.kiev.kpi.sc.parser.node.Start;
import ua.kiev.kpi.sc.parser.parser.Parser;

public class AbstractTest {
	protected Start syntaxTree;
	protected RootScope rootScope;
	protected InterimRepresentationBuilder interimBuilder;
	protected Throwable caught;	
	private Lexer lexer;
	private Parser parser;
	
	protected void load(String name)
	{
		Reader file = getFile(name);
		try {
			lexer = new Lexer(new PushbackReader(file));
			parser = new Parser(lexer);
			syntaxTree = parser.parse();
			ScopeTreeBuilder b = new ScopeTreeBuilder();
			syntaxTree.apply(b);
			rootScope = b.getRootScope();
			
			interimBuilder = new InterimRepresentationBuilder(rootScope);
			syntaxTree.apply(interimBuilder);
			
			LinkedList<Translation> stack = interimBuilder.getFilteredPolizStack();
			TypeEvaluator e = new TypeEvaluator();
			e.evaluate(stack);
		} catch (Throwable ex) {			
			caught = ex;
			/*
			System.out.println("===============================\n  ");
			System.out.println(name);
			System.out.println("===============================\n");			
			System.out.print(Lexer.run(getFile(name)));
			caught.printStackTrace();
			System.out.println("\n\n\n\n");*/
		}
	}

	private FileReader getFile(String name)  {
		try {
			return new FileReader("integrationtest//"+getClass().getPackage().getName().replace(".", "//")+"//"+name+".txt");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void assertErrMsg(String pattern)
	{
		Assert.assertNotNull("Error is expected to be thrown", caught);
		String str = caught.getMessage();
		if (str == null) {
			throw new RuntimeException(caught);
		}
		String softPattern = pattern.replace(" ", ".*");
		boolean matches = str.matches(".*"+softPattern+".*");
		Assert.assertTrue("Error message doesn't matches the pattern" +
				"\n"+str+
				"\n"+pattern, matches);
	}
	
	protected void assertErrMsg()
	{
		assertErrMsg("---");
	}
	
	protected void assertOk() throws Throwable
	{
		if (caught != null) {
			throw caught;
		}
	}
}
