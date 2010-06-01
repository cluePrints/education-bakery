package ua.kiev.kpi.sc.parser.etc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class AnalysisDelegatorGenerator {
	public static String EXP = "\\s*void\\s*(\\w*)\\((\\w*)\\s*(\\w*)\\);\\s*";
	@Test
	public void regexp()
	{
		Pattern p = Pattern.compile(EXP);
		Matcher m = p.matcher("   void caseARecursiveElementalExpression(ARecursiveElementalExpression node);  ");
		Assert.assertTrue(m.matches());
		Assert.assertEquals("caseARecursiveElementalExpression", m.group(1));
		Assert.assertEquals("ARecursiveElementalExpression", m.group(2));
		Assert.assertEquals("node", m.group(3));
	}
	
	@Test
	public void test() throws Exception
	{
		BufferedReader r = new BufferedReader(new FileReader("src\\ua\\kiev\\kpi\\sc\\parser\\analysis\\Analysis.java"));
		String s = null;
		Pattern p = Pattern.compile(EXP);		
		System.out.print("public class AnalysisDelegator implements Analysis {\n" +
							"private List<Analysis> analysis;\n");
		do {
			s = r.readLine();
			if (s != null) {
				Matcher m = p.matcher(s);
				if (m.matches()) {
					String name = m.group(1);
					String paramClass = m.group(2);
					String paramName = m.group(3);
					
					System.out.printf("\npublic void %1$s(%2$s %3$s){    " +
									  "\n   for (Analysis a : analysis) {" +
									  "\n      a.%1$s(%3$s);             " +
								      "\n   }                            " +
									  "\n}\n                             ", 
									  name, paramClass, paramName);
				}
			}
		} while (s != null);
		System.out.println("}");
	}
}
