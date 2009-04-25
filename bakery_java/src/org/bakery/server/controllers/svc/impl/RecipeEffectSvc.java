package org.bakery.server.controllers.svc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.domain.production.RecipeEffect;

public class RecipeEffectSvc extends AbstractCommand{
	@Override
	public void init(SvcController controller) throws Exception {
		setMainDAO(controller.getDAOFacade().getRecipeDAO());
		setCommand(new RecipeEffect());
	}
	@Override
	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, SvcController controller,
			AbstractFormMode mode) throws Exception {
	}
}
