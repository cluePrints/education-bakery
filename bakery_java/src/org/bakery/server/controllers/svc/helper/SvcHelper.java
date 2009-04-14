package org.bakery.server.controllers.svc.helper;

import java.io.PrintWriter;
import java.util.List;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.persistence.AbstractDAO;

public class SvcHelper {
	public static void writeAvailable(PrintWriter out, AbstractDAO dao, String sectionId) throws Exception{
		List<BusinessEntity> entities = dao.getAvailable();
		out.write("<"+sectionId+">");
		for (BusinessEntity e : entities) {
			out.write(e.toXml());
		}
		out.write("</"+sectionId+">");
		out.flush();
	}
	
	public static void write(PrintWriter out, AbstractDAO dao, String sectionId) throws Exception{
		List<BusinessEntity> entities = dao.searchByName("%", 1, 200);
		out.write("<"+sectionId+">");
		for (BusinessEntity e : entities) {
			out.write(e.toXml());
		}
		out.write("</"+sectionId+">");
		out.flush();
	}
}
