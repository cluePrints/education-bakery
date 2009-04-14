package org.bakery.server.controllers.action;

import org.bakery.server.persistence.dao.ContragentDAO;
import org.bakery.server.persistence.dao.ProductTypeDAO;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class PriceListFormAction extends AbstractFormAction{
	private ContragentDAO contragentDAO;
	private ProductTypeDAO productTypeDAO;

	public static final String AN_DEFAULT_LIST = "priceLists";
	public PriceListFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {		
		context.getRequestScope().put(ContragentFormAction.AN_DEFAULT_LIST, contragentDAO.getAvailable());
		context.getRequestScope().put(ProductTypeFormAction.AN_DEFAULT_LIST, productTypeDAO.getAvailable());
		return super.setupForm(context);			
	}
	
	public ContragentDAO getContragentDAO() {
		return contragentDAO;
	}
	public void setContragentDAO(ContragentDAO contragentDAO) {
		this.contragentDAO = contragentDAO;
	}
	public ProductTypeDAO getProductTypeDAO() {
		return productTypeDAO;
	}
	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}
}