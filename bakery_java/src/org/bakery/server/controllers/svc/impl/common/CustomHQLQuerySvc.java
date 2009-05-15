package org.bakery.server.controllers.svc.impl.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.domain.BusinessEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public class CustomHQLQuerySvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		String query = request.getParameter("query");
		String collectionName = request.getParameter("collectionName");
		if (collectionName == null)
			collectionName = "unsorted";
		if (query == null || query.trim().length()==0) 
			throw new IllegalArgumentException("'Query' request parameter should not be null");
		SessionFactory f = controller.getDAOFacade().getAccountDAO().getSessionFactory();
		Session s = f.openSession();
			Query q = s.createQuery(query);
			List results = q.list();
			StringBuilder b = new StringBuilder((results.size()+1)*100);
			for (Object o : results) {
				if (o instanceof BusinessEntity) {
					b.append("\n");
					b.append(((BusinessEntity) o).toXml());
				}
			}
			response.getWriter().write("<"+collectionName+">");
			response.getWriter().write(b.toString());
			response.getWriter().write("</"+collectionName+">");
		s.close();
	}

}
