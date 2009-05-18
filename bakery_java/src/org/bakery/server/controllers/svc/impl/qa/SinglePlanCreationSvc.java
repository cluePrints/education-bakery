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

public class SinglePlanCreationSvc implements ControllerAwareCommand{
	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		PrintWriter out = response.getWriter();		
		SvcHelper.writeCurrentDate(controller.getDAOFacade(), out);
		/* recommend some values here */
	}
}
