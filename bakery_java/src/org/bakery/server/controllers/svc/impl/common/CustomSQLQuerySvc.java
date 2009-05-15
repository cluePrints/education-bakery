package org.bakery.server.controllers.svc.impl.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.domain.BusinessEntity;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public class CustomSQLQuerySvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		String query = request.getParameter("query");
		String collectionName = request.getParameter("collectionName");
		String objName = request.getParameter("objName");
		if (collectionName == null)
			throw new IllegalArgumentException("'collectionName' request parameter should not be null");
		if (objName == null)
			throw new IllegalArgumentException("'objName' request parameter should not be null");
		if (query == null || query.trim().length()==0) 
			throw new IllegalArgumentException("'query' request parameter should not be null");
		SessionFactory f = controller.getDAOFacade().getAccountDAO().getSessionFactory();
		Session s = f.openSession();
			Connection conn = s.connection();
			PreparedStatement pr = conn.prepareStatement(query);
			ResultSet resultSet = pr.executeQuery();
			int nCols = resultSet.getMetaData().getColumnCount();
			StringBuilder tmp = new StringBuilder();
			while (resultSet.next()) {
				tmp.append("\n<"+objName+">");
				for (int i=1; i<=nCols; i++){
					Object obj = resultSet.getObject(i);
					String propName = resultSet.getMetaData().getColumnLabel(i);					
					tmp.append("\n<").append(propName).append(">");	//<paramName>			
					tmp.append(BusinessEntity.valueToString(obj));
					tmp.append("</").append(propName).append(">");  //</paramName>							
				}
				tmp.append("</"+objName+">");
			}
			response.getWriter().write(tmp.toString());
		s.close();
	}

}
