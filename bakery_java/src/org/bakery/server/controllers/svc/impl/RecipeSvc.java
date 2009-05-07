package org.bakery.server.controllers.svc.impl;

import java.io.PrintWriter;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.production.Recipe;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RecipeSvc extends AbstractCommand{
	@Override
	public void init(SvcController controller) throws Exception {
		setMainDAO(controller.getDAOFacade().getRecipeDAO());
		setCommand(new Recipe());
	}
	@Override
	protected void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller,
			AbstractFormMode mode) throws Exception {
		try{
			long id = Long.parseLong(request.getParameter("spec_id"));
			if (AbstractFormMode.SPECIAL_ADD.equals(mode)){
				
				addParameter(getCommand().getId(), id);
			} else if (AbstractFormMode.SPECIAL_REMOVE.equals(mode)){
				
				
				removeParameter(getCommand().getId(), id);
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
	
	private void addParameter(Long recipe_id, Long param_id) throws Exception{
		Session session = getMainDAO().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query q = session.createSQLQuery("SELECT * FROM recip_parameters WHERE recip_id=:recipeId");
			q.setParameter("recipeId", recipe_id);
			if (q.list().size() > 0) {
				q = session.createSQLQuery(
					"SELECT device_parameter_id FROM "+
						"(SELECT device_id FROM " +
						"(SELECT device_parameter_id FROM recip_parameters WHERE recip_id=:recipeId) AS dp " +
						"JOIN device_parameters WHERE device_parameters.device_parameter_id=dp.device_parameter_id) AS devid "+
						"JOIN device_parameters WHERE device_parameters.device_id = devid.device_id AND device_parameters.device_parameter_id=:deviceParameterId");
				q.setParameter("recipeId", recipe_id);
				q.setParameter("deviceParameterId", param_id);
				if (q.list().size() == 0)
					return;
			}
			
			PreparedStatement stmt = session.connection().prepareStatement("INSERT INTO recip_parameters VALUES(?, ?)");
			stmt.setObject(1, recipe_id);
			stmt.setObject(2, param_id);
			stmt.execute();
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			session.close();
		}
	}
	
	private void removeParameter(Long recipe_id, Long param_id) throws Exception{
		Session session = getMainDAO().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {			
			PreparedStatement stmt = session.connection().prepareStatement("DELETE FROM recip_parameters WHERE recip_id=? AND device_parameter_id=?");
			stmt.setObject(1, recipe_id);
			stmt.setObject(2, param_id);
			stmt.execute();
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			session.close();
		}
	}
}

