package org.bakery.server.controllers.svc.impl.qa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.hibernate.Session;

public class ProductionStateReportSvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {		
		SvcHelper.writeCurrentDate(controller.getDAOFacade(), response.getWriter());
		
		Session session = controller.getDAOFacade().getProductionPlanDAO().getSessionFactory().openSession();
		Connection conn = session.connection();
		PreparedStatement stmt = conn.prepareStatement(DeviceStateReportSvc.SQL_CURRENTLY_WORKING_PLANS);		
		
		ResultSet res = stmt.executeQuery();
		StringBuilder b = new StringBuilder();
		while (res.next()){
			String deviceName = res.getString("device_name");			
			b.append("\n<productionProcess>");
			b.append("<deviceName>");
			b.append(deviceName);
			b.append("</deviceName>");
			b.append("<hoursLeft>");
			b.append(res.getObject("hoursleft"));
			b.append("</hoursLeft>");
			b.append("\n</productionProcess>");
		}
		response.getWriter().write(b.toString());
		session.close();
	}
}
