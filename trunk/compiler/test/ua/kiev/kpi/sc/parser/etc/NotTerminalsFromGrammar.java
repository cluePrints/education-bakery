package ua.kiev.kpi.sc.parser.etc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Sets;

@Ignore
public class NotTerminalsFromGrammar {
	private static final String TXT = "ô<A>ô";
	private static final String REGEX = "(<.*?>)";
	
	@Test
	public void testMatch()
	{
		Assert.assertTrue(TXT.matches(REGEX));
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(TXT);
		Assert.assertEquals(m.group(1), "<A>");
	}
	
	@Test
	public void doThat() throws Exception
	{
		BufferedReader r = new BufferedReader(new FileReader("gram.txt"));
		String s;
		Pattern p = Pattern.compile(REGEX);
		Set<String> results = Sets.newTreeSet(String.CASE_INSENSITIVE_ORDER);
		do {
			s = r.readLine();
			if (s != null) {				
				Matcher m = p.matcher(s);
				while (m.find()) {
					String match = m.group();
					results.add(match.replaceAll("\\s*<\\s*", "<").replace("\\s*>\\s*", ""));
				}
			}
		} while (s != null);
		
		System.out.println(results.toString());
	}
}
