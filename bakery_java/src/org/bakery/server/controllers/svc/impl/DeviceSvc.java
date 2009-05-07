package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.hardware.Device;

public class DeviceSvc extends AbstractCommand {


	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getDAOFacade().getDeviceDAO());
		setCommand(new Device());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller, AbstractFormMode mode) throws Exception {
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getDeviceParameterDAO(), "deviceParameters");
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getUnitDAO(), "units");
		SvcHelper.writeAvailable(response.getWriter(), controller.getDAOFacade().getUnitDAO(), "unitsAvailable");
	}
}
