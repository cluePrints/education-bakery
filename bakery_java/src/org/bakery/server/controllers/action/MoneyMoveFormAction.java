package org.bakery.server.controllers.action;

import java.util.List;

import org.bakery.server.controllers.beans.AbstractFormBean;
import org.bakery.server.domain.log.MoneyMove;
import org.bakery.server.persistence.dao.AccountDAO;
import org.bakery.server.persistence.dao.MoneyMoveDAO;
import org.bakery.server.persistence.dao.OrderDAO;
import org.bakery.server.persistence.dao.PriceDAO;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


/**
 * @author Mediaspectrum, Inc.
 */
public class MoneyMoveFormAction extends AbstractFormAction{
	private AccountDAO accountDAO;
	private OrderDAO orderDAO;
	private PriceDAO priceDAO;
	public static final String AN_DEFAULT_LIST = "moneyMoves";
	public MoneyMoveFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
	@Override
	public Event setupForm(RequestContext context) throws Exception {			
		context.getRequestScope().put(OrderFormAction.AN_DEFAULT_LIST, orderDAO.getAvailable());
		context.getRequestScope().put(PriceFormAction.AN_DEFAULT_LIST, priceDAO.getAvailable());
		context.getFlashScope().put(AccountFormAction.AN_DEFAULT_LIST, accountDAO.getAvailable());
		Long orderId = 0L;
		try{
			orderId = java.lang.Long.parseLong(context.getFlowScope().getString("orderId"));
		} catch (NumberFormatException e) {
			
		}
		AbstractFormBean form = (AbstractFormBean) getFormObject(context);
		Event parentEvent = super.setupForm(context);
		if (orderId != null && orderId>0) {
			context.getFlashScope().remove(AN_DEFAULT_LIST);
			List<MoneyMove> items = ((MoneyMoveDAO) getMainDAO()).fetchByParentId(orderId);						
			context.getFlashScope().put(AN_DEFAULT_LIST, items);			
		}
		return parentEvent;
	}
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	public PriceDAO getPriceDAO() {
		return priceDAO;
	}
	public void setPriceDAO(PriceDAO priceDAO) {
		this.priceDAO = priceDAO;
	}
}