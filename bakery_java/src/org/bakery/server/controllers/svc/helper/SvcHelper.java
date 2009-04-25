package org.bakery.server.controllers.svc.helper;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	
	public static Date parseDate(String s) throws Exception{
		return df.parse(s);
	}
	
	public static String dateToString(Date d){
		return df.format(d);
	}
}
