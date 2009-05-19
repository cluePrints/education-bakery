package org.bakery.server.controllers.svc.impl.qa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.hibernate.Session;

public class RecipeGainSvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {		
		Date currDate = controller.getDAOFacade().getAccountDAO().getCurrentDate();
		String sql = controller.getDAOFacade().getQueries().get("getRecipeGains");
		
		Session session = controller.getDAOFacade().getProductionPlanDAO().getSessionFactory().openSession();
		Connection conn = session.connection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setObject(1, currDate);
		stmt.setObject(2, currDate);
		
		ResultSet res = stmt.executeQuery();
		StringBuilder b = new StringBuilder();
		while (res.next()){
			String deviceName = res.getString("device_name");			
			b.append("\n<productionProcess>");
			b.append("<deviceName>");
			b.append(deviceName);
			b.append("</deviceName>");
			b.append("<minutesLeft>");
			b.append(res.getObject("minutesleft"));
			b.append("</minutesLeft>");
			b.append("\n</productionProcess>");
		}
		response.getWriter().write(b.toString());
		session.close();
	}
}
