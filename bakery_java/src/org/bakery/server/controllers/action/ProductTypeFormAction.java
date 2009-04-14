package org.bakery.server.controllers.action;

import org.bakery.server.persistence.dao.UnitDAO;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class ProductTypeFormAction extends AbstractFormAction{
	private UnitDAO unitDAO;
	public static final String AN_DEFAULT_LIST = "productTypes";
	public ProductTypeFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {		
		context.getRequestScope().put(UnitFormAction.AN_DEFAULT_LIST, unitDAO.getAvailable());
		return super.setupForm(context);
	}
	public UnitDAO getUnitDAO() {
		return unitDAO;
	}
	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}
}