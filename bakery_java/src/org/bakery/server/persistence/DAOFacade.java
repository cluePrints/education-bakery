package org.bakery.server.persistence;

public class DAOFacade {
	private AbstractDAO addressDAO;
	private AbstractDAO accountDAO;
	private AbstractDAO contragentDAO;
	private AbstractDAO moneyMoveDAO;
	private AbstractDAO orderDAO;
	private AbstractDAO priceDAO;
	private AbstractDAO priceListDAO;
	private AbstractDAO productMoveDAO;
	private AbstractDAO productTypeDAO;
	private AbstractDAO unitDAO;
	private AbstractDAO warehouseDAO;
	public AbstractDAO getAddressDAO() {
		return addressDAO;
	}
	public void setAddressDAO(AbstractDAO addressDAO) {
		this.addressDAO = addressDAO;
	}
	public AbstractDAO getAccountDAO() {
		return accountDAO;
	}
	public void setAccountDAO(AbstractDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	public AbstractDAO getContragentDAO() {
		return contragentDAO;
	}
	public void setContragentDAO(AbstractDAO contragentDAO) {
		this.contragentDAO = contragentDAO;
	}
	public AbstractDAO getMoneyMoveDAO() {
		return moneyMoveDAO;
	}
	public void setMoneyMoveDAO(AbstractDAO moneyMoveDAO) {
		this.moneyMoveDAO = moneyMoveDAO;
	}
	public AbstractDAO getOrderDAO() {
		return orderDAO;
	}
	public void setOrderDAO(AbstractDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	public AbstractDAO getPriceDAO() {
		return priceDAO;
	}
	public void setPriceDAO(AbstractDAO priceDAO) {
		this.priceDAO = priceDAO;
	}
	public AbstractDAO getPriceListDAO() {
		return priceListDAO;
	}
	public void setPriceListDAO(AbstractDAO priceListDAO) {
		this.priceListDAO = priceListDAO;
	}
	public AbstractDAO getProductMoveDAO() {
		return productMoveDAO;
	}
	public void setProductMoveDAO(AbstractDAO productMoveDAO) {
		this.productMoveDAO = productMoveDAO;
	}
	public AbstractDAO getProductTypeDAO() {
		return productTypeDAO;
	}
	public void setProductTypeDAO(AbstractDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}
	public AbstractDAO getUnitDAO() {
		return unitDAO;
	}
	public void setUnitDAO(AbstractDAO unitDAO) {
		this.unitDAO = unitDAO;
	}
	public AbstractDAO getWarehouseDAO() {
		return warehouseDAO;
	}
	public void setWarehouseDAO(AbstractDAO warehouseDAO) {
		this.warehouseDAO = warehouseDAO;
	}
}
