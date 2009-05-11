package org.bakery.server.persistence;

public class DAOFacade {
	private AbstractDAO addressDAO;
	private AbstractDAO deviceDAO;
	private AbstractDAO accountDAO;
	private AbstractDAO contragentDAO;
	private AbstractDAO moneyMoveDAO;
	private AbstractDAO orderDAO;
	private AbstractDAO priceListItemDAO;
	private AbstractDAO priceListDAO;
	private AbstractDAO productMoveDAO;
	private AbstractDAO productTypeDAO;
	private AbstractDAO unitDAO;
	private AbstractDAO warehouseDAO;
	private AbstractDAO deviceParameterDAO;
	private AbstractDAO recipeDAO;
	private AbstractDAO productionPlanDAO;
	private AbstractDAO recipeEffectDAO;
	private AbstractDAO measureDAO;
	private AbstractDAO recipeEffectPartDAO;
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
	public AbstractDAO getPriceListItemDAO() {
		return priceListItemDAO;
	}
	public void setPriceListItemDAO(AbstractDAO priceDAO) {
		this.priceListItemDAO = priceDAO;
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
	public AbstractDAO getDeviceDAO() {
		return deviceDAO;
	}
	public void setDeviceDAO(AbstractDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}
	public AbstractDAO getDeviceParameterDAO() {
		return deviceParameterDAO;
	}
	public void setDeviceParameterDAO(AbstractDAO deviceParameterDAO) {
		this.deviceParameterDAO = deviceParameterDAO;
	}
	public AbstractDAO getRecipeDAO() {
		return recipeDAO;
	}
	public void setRecipeDAO(AbstractDAO recipeDAO) {
		this.recipeDAO = recipeDAO;
	}
	public AbstractDAO getRecipeEffectDAO() {
		return recipeEffectDAO;
	}
	public void setRecipeEffectDAO(AbstractDAO recipeEffectDAO) {
		this.recipeEffectDAO = recipeEffectDAO;
	}
	public AbstractDAO getProductionPlanDAO() {
		return productionPlanDAO;
	}
	public void setProductionPlanDAO(AbstractDAO productionPlanDAO) {
		this.productionPlanDAO = productionPlanDAO;
	}
	public AbstractDAO getMeasureDAO() {
		return measureDAO;
	}
	public void setMeasureDAO(AbstractDAO measureDAO) {
		this.measureDAO = measureDAO;
	}
	public AbstractDAO getRecipeEffectPartDAO() {
		return recipeEffectPartDAO;
	}
	public void setRecipeEffectPartDAO(AbstractDAO recipeEffectPartDAO) {
		this.recipeEffectPartDAO = recipeEffectPartDAO;
	}
}
