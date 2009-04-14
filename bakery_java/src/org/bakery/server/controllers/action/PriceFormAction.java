package org.bakery.server.controllers.action;

import java.util.List;

import org.bakery.server.controllers.beans.AbstractFormBean;
import org.bakery.server.domain.pricing.PriceListItem;
import org.bakery.server.persistence.dao.PriceDAO;
import org.bakery.server.persistence.dao.PriceListDAO;
import org.bakery.server.persistence.dao.ProductTypeDAO;
import org.hibernate.Session;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class PriceFormAction extends AbstractFormAction{
	private ProductTypeDAO productTypeDAO;
	private PriceListDAO priceListDAO;
	public static final String AN_DEFAULT_LIST = "prices";
	public PriceFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {	
		context.getRequestScope().put(ProductTypeFormAction.AN_DEFAULT_LIST, productTypeDAO.getAvailable());
		context.getRequestScope().put(PriceListFormAction.AN_DEFAULT_LIST, priceListDAO.getAvailable());
		Long priceId = 0L;
		try{
			priceId = java.lang.Long.parseLong(context.getFlowScope().getString("priceId"));
		} catch (NumberFormatException e) {
			
		}
		AbstractFormBean form = (AbstractFormBean) getFormObject(context);
		Event parentEvent = super.setupForm(context);
		if (priceId != null && priceId>0) {
			context.getFlashScope().remove(AN_DEFAULT_LIST);
			List<PriceListItem> items = ((PriceDAO) getMainDAO()).fetchByParentId(priceId);						
			context.getFlashScope().put(AN_DEFAULT_LIST, items);
		}
		return parentEvent;
	}

	public ProductTypeDAO getProductTypeDAO() {
		return productTypeDAO;
	}
	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}
	public PriceListDAO getPriceListDAO() {
		return priceListDAO;
	}
	public void setPriceListDAO(PriceListDAO priceListDAO) {
		this.priceListDAO = priceListDAO;
	}
}