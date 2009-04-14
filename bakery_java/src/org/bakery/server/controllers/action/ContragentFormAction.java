package org.bakery.server.controllers.action;

import org.bakery.server.persistence.dao.AddressDAO;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class ContragentFormAction extends AbstractFormAction{
	private AddressDAO addressDAO;
	public static final String AN_DEFAULT_LIST = "contragents";
	public ContragentFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {		
		context.getRequestScope().put(AddressFormAction.AN_DEFAULT_LIST, addressDAO.getAvailable());
		return super.setupForm(context);
	}
	public AddressDAO getAddressDAO() {
		return addressDAO;
	}
	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}
}