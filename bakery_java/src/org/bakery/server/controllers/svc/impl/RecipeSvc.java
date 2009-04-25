package org.bakery.server.controllers.svc.impl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.hardware.DeviceParameter;
import org.bakery.server.domain.production.Recipe;

public class RecipeSvc extends AbstractCommand{
	@Override
	public void init(SvcController controller) throws Exception {
		setMainDAO(controller.getDAOFacade().getRecipeDAO());
		setCommand(new Recipe());
	}
	@Override
	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, SvcController controller,
			AbstractFormMode mode) throws Exception {
		try{			
			if (AbstractFormMode.SPECIAL_ADD.equals(mode)){
				long id = Long.parseLong(request.getParameter("id"));
				DeviceParameter p = (DeviceParameter) controller.getDAOFacade().getDeviceParameterDAO().getById(id);
				((Recipe) this.getCommand()).getParameters().add(p);
				controller.getDAOFacade().getRecipeDAO().saveOrUpdate((Recipe) this.getCommand());
			} else if (AbstractFormMode.SPECIAL_REMOVE.equals(mode)){
				long id = Long.parseLong(request.getParameter("id"));
				DeviceParameter p = (DeviceParameter) controller.getDAOFacade().getDeviceParameterDAO().getById(id);
				((Recipe) this.getCommand()).getParameters().remove(p);
				controller.getDAOFacade().getRecipeDAO().saveOrUpdate((Recipe) this.getCommand());
			}
		} catch (Exception e){
			// TODO: fix me
		}
		PrintWriter out = response.getWriter();
		SvcHelper.write(out, controller.getDAOFacade().getProductTypeDAO(), "productTypes");
		SvcHelper.write(out, controller.getDAOFacade().getDeviceParameterDAO(), "deviceParameters");
		SvcHelper.write(out, controller.getDAOFacade().getDeviceDAO(), "devices");
		SvcHelper.write(out, controller.getDAOFacade().getRecipeEffectDAO(), "recipeEffects");
	}
}
