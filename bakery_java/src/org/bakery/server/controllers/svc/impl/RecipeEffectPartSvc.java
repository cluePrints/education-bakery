package org.bakery.server.controllers.svc.impl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.production.RecipeEffectPart;

public class RecipeEffectPartSvc extends AbstractAdminCommand{
	@Override
	public void init(SvcController controller) throws Exception {
		setMainDAO(controller.getDAOFacade().getRecipeEffectPartDAO());
		setCommand(new RecipeEffectPart());
	}
	@Override
	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller,
			AbstractFormMode mode) throws Exception {
		PrintWriter out = response.getWriter();
		SvcHelper.write(out, controller.getDAOFacade().getProductTypeDAO(), "productTypes");
		SvcHelper.write(out, controller.getDAOFacade().getDeviceParameterDAO(), "deviceParameters");
		SvcHelper.write(out, controller.getDAOFacade().getDeviceDAO(), "devices");
		SvcHelper.write(out, controller.getDAOFacade().getRecipeEffectDAO(), "recipeEffects");
		SvcHelper.write(out, controller.getDAOFacade().getRecipeDAO(), "recipes");
		SvcHelper.write(out, controller.getDAOFacade().getRecipeEffectPartDAO(), "recipeEffectParts");
	}
}
