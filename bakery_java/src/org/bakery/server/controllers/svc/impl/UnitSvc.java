package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.domain.production.Unit;

public class UnitSvc extends AbstractAdminCommand {


	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getDAOFacade().getUnitDAO());
		setCommand(new Unit());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller, AbstractFormMode mode) throws Exception {
		/*List<BusinessEntity> entities = controller.getDAOFacade().getUnitDAO().getAvailable();
		PrintWriter out = response.getWriter();
		out.write("<availableUnits>");
		for (BusinessEntity e : entities) {
			out.write(e.toXml());
		}
		out.write("</availableUnits>");
		out.flush();*/
	}
}
