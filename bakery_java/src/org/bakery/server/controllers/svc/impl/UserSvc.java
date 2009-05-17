package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.domain.fake.User;

public class UserSvc extends AbstractAdminCommand {

	@Override
	public void init(SvcController controller) {
		setMainDAO(controller.getDAOFacade().getUserDAO());
		setCommand(new User());
	}

	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller,
			AbstractFormMode mode) throws Exception {
	}
}
