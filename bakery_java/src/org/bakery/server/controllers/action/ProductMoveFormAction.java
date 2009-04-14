
package org.bakery.server.controllers.action;

import java.util.List;

import org.bakery.server.controllers.beans.AbstractFormBean;
import org.bakery.server.domain.log.ProductMove;
import org.bakery.server.persistence.dao.MoneyMoveDAO;
import org.bakery.server.persistence.dao.ProductMoveDAO;
import org.bakery.server.persistence.dao.ProductTypeDAO;
import org.bakery.server.persistence.dao.WarehouseDAO;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class ProductMoveFormAction extends AbstractFormAction{
	private ProductTypeDAO productTypeDAO;
	private WarehouseDAO warehouseDAO;
	private MoneyMoveDAO moneyMoveDAO;
	public static final String AN_DEFAULT_LIST = "productMoves";
	public ProductMoveFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {	
		// XXX: We could only use accounts of contragents, linked in operation		
		context.getRequestScope().put(ProductTypeFormAction.AN_DEFAULT_LIST, productTypeDAO.getAvailable());
		context.getRequestScope().put(WarehouseFormAction.AN_DEFAULT_LIST, warehouseDAO.getAvailable());
		context.getFlashScope().put(MoneyMoveFormAction.AN_DEFAULT_LIST, moneyMoveDAO.getAvailable());
		Long moneyMoveId = 0L;
		try{
			moneyMoveId = java.lang.Long.parseLong(context.getFlowScope().getString("moneyMoveId"));
		} catch (NumberFormatException e) {
			
		}
		AbstractFormBean form = (AbstractFormBean) getFormObject(context);
		Event parentEvent = super.setupForm(context);
		if (moneyMoveId != null && moneyMoveId>0) {
			context.getFlashScope().remove(AN_DEFAULT_LIST);
			List<ProductMove> items = ((ProductMoveDAO) getMainDAO()).fetchByParentId(moneyMoveId);						
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
	public WarehouseDAO getWarehouseDAO() {
		return warehouseDAO;
	}
	public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
		this.warehouseDAO = warehouseDAO;
	}
	public MoneyMoveDAO getMoneyMoveDAO() {
		return moneyMoveDAO;
	}
	public void setMoneyMoveDAO(MoneyMoveDAO moneyMoveDAO) {
		this.moneyMoveDAO = moneyMoveDAO;
	}
}