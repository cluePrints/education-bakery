package org.bakery.server.controllers.svc.impl.qa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.hibernate.Session;

public class ProductionStateReportSvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {		
		Session session = controller.getDAOFacade().getProductionPlanDAO().getSessionFactory().openSession();
		session.createSQLQuery("SELECT * FROM ");
		session.close();
	}
}
