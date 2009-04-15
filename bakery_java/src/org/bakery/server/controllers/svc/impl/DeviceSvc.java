package org.bakery.server.controllers.svc.impl;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.hardware.Device;
import org.bakery.server.domain.production.Unit;

public class DeviceSvc extends AbstractCommand {


	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getDAOFacade().getDeviceDAO());
		setCommand(new Device());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, SvcController controller, AbstractFormMode mode) throws Exception {
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
