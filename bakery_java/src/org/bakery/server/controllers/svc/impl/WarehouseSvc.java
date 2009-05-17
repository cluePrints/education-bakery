package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.production.Warehouse;

public class WarehouseSvc extends AbstractAdminCommand {

	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getDAOFacade().getWarehouseDAO());
		setCommand(new Warehouse());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller,
			AbstractFormMode mode) throws Exception {
		SvcHelper.writeAvailable(response.getWriter(), controller.getDAOFacade().getAddressDAO(), "availableAddresses");
		SvcHelper.writeAvailable(response.getWriter(), controller.getDAOFacade().getContragentDAO(), "availableContragents");
		
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getAddressDAO(), "addresses");
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getContragentDAO(), "contragents");
	}
}
