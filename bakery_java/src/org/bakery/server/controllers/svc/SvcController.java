package org.bakery.server.controllers.svc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakery.server.controllers.svc.impl.AbstractCommand;
import org.bakery.server.persistence.dao.AccountDAO;
import org.bakery.server.persistence.dao.AddressDAO;
import org.bakery.server.persistence.dao.ContragentDAO;
import org.bakery.server.persistence.dao.MoneyMoveDAO;
import org.bakery.server.persistence.dao.OrderDAO;
import org.bakery.server.persistence.dao.PriceDAO;
import org.bakery.server.persistence.dao.PriceListDAO;
import org.bakery.server.persistence.dao.ProductMoveDAO;
import org.bakery.server.persistence.dao.ProductTypeDAO;
import org.bakery.server.persistence.dao.UnitDAO;
import org.bakery.server.persistence.dao.WarehouseDAO;
import org.bakery.server.util.LoggingUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class SvcController extends AbstractController {
	private AddressDAO addressDAO;
	private AccountDAO accountDAO;
	private ContragentDAO contragentDAO;
	private MoneyMoveDAO moneyMoveDAO;
	private OrderDAO orderDAO;
	private PriceDAO priceDAO;
	private PriceListDAO priceListDAO;
	private ProductMoveDAO productMoveDAO;
	private ProductTypeDAO productTypeDAO;
	private UnitDAO unitDAO;
	private WarehouseDAO warehouseDAO;
	private Map<String, String> services = new HashMap(1);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String svcName = request.getParameter("svc");
		// FIXME: it's dumb approach

		try {
			logger.info(LoggingUtils.dumpStringMapIntoString(request.getParameterMap()));
			String svcClassName = svcName;
			Class svcClass = Class.forName(svcClassName);
			AbstractCommand command = (AbstractCommand) svcClass.newInstance();
			command.init(this);
			command.execute(request, response, this);
			
		} catch (Exception ex) {
			response.setStatus(500);
			response.flushBuffer();				
			throw ex;
		}
		return null;
	}

	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public ContragentDAO getContragentDAO() {
		return contragentDAO;
	}

	public void setContragentDAO(ContragentDAO contragentDAO) {
		this.contragentDAO = contragentDAO;
	}

	public MoneyMoveDAO getMoneyMoveDAO() {
		return moneyMoveDAO;
	}

	public void setMoneyMoveDAO(MoneyMoveDAO moneyMoveDAO) {
		this.moneyMoveDAO = moneyMoveDAO;
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

	public PriceListDAO getPriceListDAO() {
		return priceListDAO;
	}

	public void setPriceListDAO(PriceListDAO priceListDAO) {
		this.priceListDAO = priceListDAO;
	}

	public ProductMoveDAO getProductMoveDAO() {
		return productMoveDAO;
	}

	public void setProductMoveDAO(ProductMoveDAO productMoveDAO) {
		this.productMoveDAO = productMoveDAO;
	}

	public ProductTypeDAO getProductTypeDAO() {
		return productTypeDAO;
	}

	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}

	public UnitDAO getUnitDAO() {
		return unitDAO;
	}

	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	public WarehouseDAO getWarehouseDAO() {
		return warehouseDAO;
	}

	public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
		this.warehouseDAO = warehouseDAO;
	}

	public Map<String, String> getServices() {
		return services;
	}

	public void setServices(Map<String, String> services) {
		this.services = services;
	}

}
