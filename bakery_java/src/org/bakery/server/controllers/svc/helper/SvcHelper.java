package org.bakery.server.controllers.svc.helper;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.persistence.AbstractDAO;
import org.bakery.server.util.BakeryConstants;

public class SvcHelper {
	private static SimpleDateFormat df; 
	static {
		df = new SimpleDateFormat("dd/mm/yy");
	}
	public static void writeAvailable(PrintWriter out, AbstractDAO dao, String sectionId) throws Exception{
		List<BusinessEntity> entities = dao.getAvailable();
		out.write("\n<"+sectionId+">\n");
		for (BusinessEntity e : entities) {
			out.write(e.toXml());
		}
		out.write("\n</"+sectionId+">\n");
		out.flush();
	}
	
	public static void write(PrintWriter out, AbstractDAO dao, String sectionId) throws Exception{
		List<BusinessEntity> entities = dao.searchByName("%", BakeryConstants.DEFAULT_SEARCH_START_FROM, BakeryConstants.DEFAULT_SEARCH_MAX_RESULTS);
		out.write("\n<"+sectionId+">\n");
		for (BusinessEntity e : entities) {
			out.write(e.toXml());
		}
		out.write("\n</"+sectionId+">\n");
		out.flush();
	}	
	
	
	public static String replaceXMLDeclinedCharacters(String input){
		String result = 
		"<![CDATA[" + input.replaceAll("]]>", "]]>]]><![CDATA[") + "]]>";
		return result;
	}
	public static void writeErrors(PrintWriter wr, Map<String, String> errors){
		if ((errors == null) || (errors.isEmpty()))
			return;
		Iterator<Map.Entry<String, String>> it = errors.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> e = it.next();
			wr.write("\n<errors>");
			wr.write("\n  <entity>");
			wr.write("\n    "+replaceXMLDeclinedCharacters(e.getKey()));
			wr.write("\n  </entity>");
			wr.write("\n  <message>");
			wr.write("\n    "+replaceXMLDeclinedCharacters(e.getValue()));
			wr.write("\n  </message>");
			wr.write("\n</errors>");
		}
		wr.flush();
	}
	
	public static Date parseDate(String s) throws Exception{
		return df.parse(s);
	}
	
	public static String dateToString(Date d){
		return df.format(d);
	}
}
