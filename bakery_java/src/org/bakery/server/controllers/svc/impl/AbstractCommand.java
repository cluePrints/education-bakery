package org.bakery.server.controllers.svc.impl;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.persistence.AbstractDAO;

public abstract class AbstractCommand implements ControllerAwareCommand {
	private AbstractDAO mainDAO;
	
	/**
	 * Entity, that represents allowed incoming parameters, 
	 * that bind to it from request parameters
	 */
	private BusinessEntity command;
	/**
	 * Also 'mode' request parameter is used to determine what is expected
	 */

	public void execute(HttpServletRequest request,
			HttpServletResponse response, SvcController controller)
			throws Exception {
		/* Extract all from request*/
		AbstractFormMode mode = AbstractFormMode.valueOf(request.getParameter("mode"));
		
		// fill command according to request
		bindCommand(request);	
		
		// run edit/remove/restore/get
		runCommonMode(mode);
		
		// ancestors actions
		executeInternal(request, response, controller, mode);
		
		// include mainDAO entities		
		List<BusinessEntity> entities = mainDAO.searchByName("%", 1, 200);

		PrintWriter out = response.getWriter();
		SvcHelper.write(out, mainDAO, "mainData");				
		SvcHelper.writeAvailable(out, mainDAO, "available");
	}
	private void runCommonMode(AbstractFormMode mode) throws Exception {
		/* Run default action*/
		if (AbstractFormMode.EDIT.equals(mode)) {
			mainDAO.saveOrUpdate(command);
		} else if (AbstractFormMode.DELETE.equals(mode)){
			mainDAO.removeById(command.getId());
		} else if (AbstractFormMode.RESTORE.equals(mode)){
			mainDAO.restoreById(command.getId());
		} else if (AbstractFormMode.NEW.equals(mode)){
			mainDAO.save(command.getId());
		}
	}
	private void bindCommand(HttpServletRequest request)
			throws Exception {
		Class clazz = command.getClass();
		BeanInfo inf = java.beans.Introspector.getBeanInfo(clazz, Object.class);
		PropertyDescriptor[] propDescriptors = inf.getPropertyDescriptors();
		for (PropertyDescriptor prop : propDescriptors) {
			Class propType = prop.getPropertyType();
			Object propValue = null;
			String strVal = request.getParameter(prop.getName());
			if (strVal == null)
				continue;
			
			/* BusinessEntity - just try to bind id*/			
			if (BusinessEntity.class.isAssignableFrom(propType)){				
				propValue = propType.newInstance();
				((BusinessEntity) propValue).setId(Long.parseLong(strVal));				
			}
			/* Int*/
			if ((propType.isAssignableFrom(Integer.class) || propType.equals(java.lang.Integer.TYPE))
				&& (strVal.trim().length()>0)) 
					propValue = Integer.parseInt(strVal);	
			
			/* Long */
			if ((propType.isAssignableFrom(Long.class) || propType.equals(java.lang.Long.TYPE))	
				&& (strVal.trim().length()>0)) 
				propValue = Long.parseLong(strVal);
			
			/* String */
			if (propType.isAssignableFrom(String.class))				
				propValue = strVal;	
			
			/* Booleans*/
			if (propType.isAssignableFrom(Boolean.class) || propType.equals(java.lang.Boolean.TYPE))
				propValue = "1".equals(strVal);
			
			Method writeMethod = prop.getWriteMethod();
			writeMethod.invoke(command, propValue);
		}
	}
	protected abstract void executeInternal(HttpServletRequest request,
			HttpServletResponse response, SvcController controller, AbstractFormMode mode) throws Exception;
	public abstract void init(SvcController controller) throws Exception;
	public AbstractDAO getMainDAO() {
		return mainDAO;
	}
	public void setMainDAO(AbstractDAO mainDAO) {
		this.mainDAO = mainDAO;
	}
	public BusinessEntity getCommand() {
		return command;
	}
	public void setCommand(BusinessEntity command) {
		this.command = command;
	}
}
