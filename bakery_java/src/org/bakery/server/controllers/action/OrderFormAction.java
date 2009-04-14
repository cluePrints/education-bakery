package org.bakery.server.controllers.action;

import org.bakery.server.persistence.dao.ContragentDAO;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class OrderFormAction extends AbstractFormAction{
	private ContragentDAO contragentDAO;
	public static final String AN_DEFAULT_LIST = "orders";
	public OrderFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {	
		context.getRequestScope().put(ContragentFormAction.AN_DEFAULT_LIST, contragentDAO.getAvailable());
		return super.setupForm(context);
	}
	public ContragentDAO getContragentDAO() {
		return contragentDAO;
	}
	public void setContragentDAO(ContragentDAO contragentDAO) {
		this.contragentDAO = contragentDAO;
	}
}