package org.bakery.server.controllers.svc.helper;

import java.io.PrintWriter;
import java.util.List;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.persistence.AbstractDAO;
import org.bakery.server.util.BakeryConstants;

public class SvcHelper {
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
}
