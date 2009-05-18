package org.bakery.server.controllers.svc.impl.qa;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class OrderStateReportSvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		String sql = controller.getDAOFacade().getQueries().get("getOrderStatuses");
		SessionFactory f = controller.getDAOFacade().getAccountDAO().getSessionFactory();
		Session session = f.openSession();
			Connection c = session.connection();
			StringBuilder result = new StringBuilder();
			PreparedStatement stmt = c.prepareStatement(sql);			
			ResultSet results = stmt.executeQuery();
			result.append("<orderStatuses>");
			while (results.next()) {
				
				boolean payed=false;
				boolean produced = false;
				boolean shipped = false;
				boolean done = false;
				if (results.getInt("order_payed")==1)
					payed = true;
				if (results.getInt("order_produced")==1)
					produced = true;
				if (results.getInt("order_shipped")==1)
					shipped = true;
				if (results.getInt("order_done")==1)
					done = true;
				result.append("\n<orderStatus>");
				result.append("\n<id>");
				result.append(results.getInt("order_id"));
				result.append("</id>");
				
				result.append("\n<payed>");
				result.append(payed);
				result.append("</payed>");
				
				result.append("\n<produced>");
				result.append(produced);
				result.append("</produced>");
				
				result.append("\n<shipped>");
				result.append(shipped);
				result.append("</shipped>");
				
				result.append("\n<done>");
				result.append(done);
				result.append("</done>");				
				
				result.append("</orderStatus>");
			}
			result.append("</orderStatuses>");
		session.close();
		f.close();
		PrintWriter out = response.getWriter();		
		SvcHelper.write(out, controller.getDAOFacade().getOrderDAO(), "orders");
		out.write(result.toString());
	}	
}
