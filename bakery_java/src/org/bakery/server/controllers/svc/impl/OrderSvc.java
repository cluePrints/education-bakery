package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.log.Order;

public class OrderSvc extends AbstractAdminCommand {


	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getDAOFacade().getOrderDAO());
		setCommand(new Order());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller, AbstractFormMode mode) throws Exception {
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getContragentDAO(), "contragents");
	}
}
