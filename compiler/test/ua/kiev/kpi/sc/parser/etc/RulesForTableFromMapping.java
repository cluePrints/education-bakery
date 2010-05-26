package ua.kiev.kpi.sc.parser.etc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Regexp frenzy, it's easier to simply look up by mapping sorted in order we want
 */
public class RulesForTableFromMapping {
	private static final String TXT = "   put(15, ABooleanLiteral.class, \"literal = literal_boolean\");   ";
	private static final String REGEX = ".*put.*\\.class,.*\"(.*)\".*";
	@Test
	public void testMatch()
	{
		Assert.assertTrue(TXT.matches(REGEX));
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(TXT);
		Assert.assertEquals(true, m.matches());
		Assert.assertEquals(m.group(1), "literal = literal_boolean");
	}
	@Test
	public void doThat() throws Exception
	{
		BufferedReader r = new BufferedReader(new FileReader("src\\ua\\kiev\\kpi\\sc\\parser\\ext\\rules\\ReduceRulesMapping.java"));
		String s;
		Pattern p = Pattern.compile(REGEX);
		Collection<String> results = Lists.newLinkedList();
		do {
			s = r.readLine();
			if (s != null) {				
				Matcher m = p.matcher(s);
				while (m.find()) {
					String match = m.group(1);
					results.add(match);
				}
			}
		} while (s != null);
		
		for (String result : results) {
			System.out.println(result.toString());
		}
		
		for (int i=0; i<84; i++) {
			System.out.println("new"+i);
		}
	}
}
