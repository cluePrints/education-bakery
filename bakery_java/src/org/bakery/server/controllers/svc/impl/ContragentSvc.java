package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.domain.accounting.Contragent;

public class ContragentSvc extends AbstractCommand {

	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getContragentDAO());
		setCommand(new Contragent());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, SvcController controller,
			AbstractFormMode mode) throws Exception {
	}
}
