package org.bakery.server.controllers.svc.impl;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.ControllerAwareCommand;
import org.bakery.server.controllers.svc.ISvcController;
import org.bakery.server.controllers.svc.SvcController;
import org.bakery.server.controllers.svc.beans.AbstractFormMode;
import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.persistence.AbstractDAO;
import org.bakery.server.validation.ValidationHelper;

public abstract class AbstractCommand implements ControllerAwareCommand {
	private AbstractDAO mainDAO;

	/**
	 * Entity, that represents allowed incoming parameters, that bind to it from
	 * request parameters
	 */
	private BusinessEntity command;

	private Map<String, String> beanValidationErrors = new HashMap<String, String>();

	/**
	 * Also 'mode' request parameter is used to determine what is expected
	 */

	public void execute(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller)
			throws Exception {
		/* Extract all from request */
		AbstractFormMode mode = AbstractFormMode.valueOf(request
				.getParameter("mode"));

		// fill command according to request
		bindCommand(request);

		if (beanValidationErrors == null || beanValidationErrors.isEmpty()) {
			// run edit/remove/restore/get
			runCommonMode(mode);
		}

		// ancestors actions
		executeInternal(request, response, controller, mode);

		PrintWriter out = response.getWriter();

		SvcHelper.write(out, mainDAO, "mainData");
		SvcHelper.writeAvailable(out, mainDAO, "available");
		SvcHelper.writeErrors(out, beanValidationErrors);
	}

	private void runCommonMode(AbstractFormMode mode) throws Exception {
		/* Run default action */
		if (AbstractFormMode.EDIT.equals(mode)) {
			mainDAO.saveOrUpdate(command);
		} else if (AbstractFormMode.DELETE.equals(mode)) {
			mainDAO.removeById(command.getId());
		} else if (AbstractFormMode.RESTORE.equals(mode)) {
			mainDAO.restoreById(command.getId());
		} else if (AbstractFormMode.NEW.equals(mode)) {
			mainDAO.save(command.getId());
		}
	}

	private void bindCommand(HttpServletRequest request) throws Exception {
		AbstractFormMode mode = AbstractFormMode.valueOf(request
				.getParameter("mode"));
		Class clazz = command.getClass();
		BeanInfo inf = java.beans.Introspector.getBeanInfo(clazz, Object.class);
		PropertyDescriptor[] propDescriptors = inf.getPropertyDescriptors();
		for (PropertyDescriptor prop : propDescriptors) {			
			Class propType = prop.getPropertyType();
			Object propValue = null;
			String strVal = request.getParameter(prop.getName());
			if (strVal == null)
				strVal = "";

			strVal = URLDecoder.decode(strVal, "utf-8");			
			try {
				/* BusinessEntity - just try to bind id */
				if (BusinessEntity.class.isAssignableFrom(propType)) {
					propValue = propType.newInstance();
					((BusinessEntity) propValue).setId(Long.parseLong(strVal));
				}
				/* Int */
				if ((propType.isAssignableFrom(Integer.class) || propType
						.equals(java.lang.Integer.TYPE))
						&& (strVal.trim().length() > 0))
					propValue = Integer.parseInt(strVal);

				/* Long */
				if ((propType.isAssignableFrom(Long.class) || propType
						.equals(java.lang.Long.TYPE))
						&& (strVal.trim().length() > 0))
					propValue = Long.parseLong(strVal);

				/* String */
				if (propType.isAssignableFrom(String.class))
					propValue = strVal;

				/* Booleans */
				if (propType.isAssignableFrom(Boolean.class)
						|| propType.equals(java.lang.Boolean.TYPE))
					propValue = "1".equals(strVal);

				/* Float */
				if (propType.isAssignableFrom(Float.class)
						|| propType.equals(java.lang.Float.TYPE))
					propValue = Float.parseFloat(strVal);

				/* Double */
				if (propType.isAssignableFrom(Double.class)
						|| propType.equals(java.lang.Double.TYPE))
					propValue = Double.parseDouble(strVal);

				/* Date */
				if (propType.isAssignableFrom(Date.class)) {
					propValue = SvcHelper.parseDate(strVal);
				}

				Method writeMethod = prop.getWriteMethod();
				writeMethod.invoke(command, propValue);

			} catch (NumberFormatException nfe) {
				if (AbstractFormMode.EDIT.equals(mode)
						|| AbstractFormMode.NEW.equals(mode))
					ValidationHelper.reportNumberFormatException(command, prop,
							beanValidationErrors);
			} catch (Exception e) {
				// validation will be later
			} finally {
				// validation
				if (AbstractFormMode.EDIT.equals(mode)
						|| AbstractFormMode.NEW.equals(mode)) {
					ValidationHelper.validateEmpty(command, prop,
							beanValidationErrors);
					ValidationHelper.validateStringLen(command, prop,
							beanValidationErrors);
					// XXX: get current date from server!
					ValidationHelper.validateGreaterThen(command, prop,
							beanValidationErrors, mainDAO.getCurrentDate());
				}
			}
		}

	}

	protected abstract void executeInternal(HttpServletRequest request,
			HttpServletResponse response, ISvcController controller,
			AbstractFormMode mode) throws Exception;

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
