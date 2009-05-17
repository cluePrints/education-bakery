package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.log.MoneyMove;

public class MoneyMoveSvc extends AbstractAdminCommand {


	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getDAOFacade().getMoneyMoveDAO());
		setCommand(new MoneyMove());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller, AbstractFormMode mode) throws Exception {
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getOrderDAO(), "orders");
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getAccountDAO(), "accounts");
		SvcHelper.write(response.getWriter(), controller.getDAOFacade().getPriceListItemDAO(), "priceListItems");
	}
}
