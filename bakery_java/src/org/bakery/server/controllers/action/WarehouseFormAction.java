package org.bakery.server.controllers.action;

import org.bakery.server.persistence.dao.AddressDAO;
import org.bakery.server.persistence.dao.ContragentDAO;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class WarehouseFormAction extends AbstractFormAction{
	private ContragentDAO contragentDAO;
	private AddressDAO addressDAO;
	public static final String AN_DEFAULT_LIST = "warehouses";
	public WarehouseFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {		
		context.getRequestScope().put(AddressFormAction.AN_DEFAULT_LIST, addressDAO.getAvailable());
		context.getRequestScope().put(ContragentFormAction.AN_DEFAULT_LIST, contragentDAO.getAvailable());
		return super.setupForm(context);
	}
	public AddressDAO getAddressDAO() {
		return addressDAO;
	}
	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}
	public ContragentDAO getContragentDAO() {
		return contragentDAO;
	}
	public void setContragentDAO(ContragentDAO contragentDAO) {
		this.contragentDAO = contragentDAO;
	}
}