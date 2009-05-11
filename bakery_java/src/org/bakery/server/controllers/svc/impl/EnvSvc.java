package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.fake.Environment;
import org.bakery.server.persistence.DAOFacade;

public class EnvSvc extends AbstractCommand {

	@Override
	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller,
			AbstractFormMode mode) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(SvcController controller) throws Exception {
		setCommand(new Environment());
		setMainDAO(controller.getDAOFacade().getEnvironmentDAO());
	}
}
