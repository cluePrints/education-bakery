package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.domain.pricing.PriceListItem;

public class PriceListItemSvc extends AbstractCommand {

	@Override
	public void init(SvcController controller) throws Exception {
		setMainDAO(controller.getDAOFacade().getPriceListItemDAO());
		setCommand(new PriceListItem());
	}
	@Override
	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller,
			AbstractFormMode mode) throws Exception {
	}
}
