package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.production.Warehouse;

public class WarehouseSvc extends AbstractCommand {

	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getWarehouseDAO());
		setCommand(new Warehouse());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, SvcController controller,
			AbstractFormMode mode) throws Exception {
		SvcHelper.writeAvailable(response.getWriter(), controller.getAddressDAO(), "availableAddresses");
		SvcHelper.writeAvailable(response.getWriter(), controller.getContragentDAO(), "availableContragents");
		
		SvcHelper.write(response.getWriter(), controller.getAddressDAO(), "addresses");
		SvcHelper.write(response.getWriter(), controller.getContragentDAO(), "contragents");
	}
}
