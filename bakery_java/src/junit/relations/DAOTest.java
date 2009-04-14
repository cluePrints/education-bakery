package junit.relations;
import java.util.List;

import junit.util.TestConst;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.domain.accounting.Account;
import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.log.MoneyMove;
import org.bakery.server.domain.log.ProductMove;
import org.bakery.server.domain.pricing.PriceList;
import org.bakery.server.domain.pricing.PriceListItem;
import org.bakery.server.domain.production.Warehouse;
import org.bakery.server.persistence.AbstractDAO;
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

public class DAOTest extends AbstractSpringTest{
	protected ProductTypeDAO productTypeDAO;
	protected UnitDAO unitDAO;
	protected AddressDAO addressDAO;
	protected ContragentDAO contragentDAO;
	protected AccountDAO accountDAO;	
	protected WarehouseDAO warehouseDAO;
	protected PriceListDAO priceListDAO;
	protected PriceDAO priceDAO;
	protected OrderDAO orderDAO;
	protected MoneyMoveDAO moneyMoveDAO;
	protected ProductMoveDAO productMoveDAO;
	public UnitDAO getUnitDAO() {
		return unitDAO;
	}

	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	public ProductTypeDAO getProductTypeDAO() {
		return productTypeDAO;
	}

	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}

	public void testDAOs() throws Exception {
		assertNotNull(orderDAO.getAccountsAvailableForOrder(4L));
		assertTrue("Order should have available account", orderDAO.getAccountsAvailableForOrder(1L).size()>0);	
		
		/* Lazy init tests*/
		assertNotNull(((Warehouse) warehouseDAO.getAvailable().get(0)).getOwner().getName());
		assertNotNull(((MoneyMove) moneyMoveDAO.getAvailable().get(1)).getSourceAccount().getOwner().getName());
		assertTrue(productMoveDAO.fetchByParentId(2L).get(0) instanceof ProductMove);
		
		assertNotNull(priceDAO.fetchByParentId(1L).get(0).getParent().getOwner().getName());
		assertNotNull(moneyMoveDAO.fetchByParentId(2L).get(0).getDestinationAccount().getOwner().getName());
		assertNotNull(((PriceList)priceListDAO.getAvailable().get(0)).getOwner().getName());
		assertNotNull(((PriceListItem)priceDAO.getAvailable().get(0)).getParent().getOwner().getName());
		assertNotNull(((Account) accountDAO.getAvailable().get(0)).getOwner().getName());
		assertNotNull(((Account) orderDAO.getAccountsAvailableForOrder(1L).get(0)).getOwner().getName());
		assertNotNull((moneyMoveDAO.fetchByParentId(2L).get(0).getOrder().getConsumer().getName()));
		assertNotNull((moneyMoveDAO.fetchByParentId(2L).get(0).getOrder().getProvider().getName()));		
		
		testSingleDAO(productMoveDAO);		
		testSingleDAO(unitDAO);		
		testSingleDAO(productTypeDAO);		
		testSingleDAO(addressDAO);
		testSingleDAO(contragentDAO);
		testSingleDAO(accountDAO);
		testSingleDAO(warehouseDAO);
		testSingleDAO(priceListDAO);
		testSingleDAO(priceDAO);
		testSingleDAO(orderDAO);
		testSingleDAO(moneyMoveDAO);			/* Lazy init tests*/		
	}
	
	protected void testAvailable(AbstractDAO dao) throws Exception {
		List<Address> allUnits = dao.searchByName("%", 0, 999);
		BusinessEntity obj = (BusinessEntity) dao.getById(TestConst.TEST_OBJECT_ID);
		obj.setActive(1);
		dao.saveOrUpdate(obj);
		List activeaddresss = dao.getAvailable();
		obj.setActive(0);
		dao.saveOrUpdate(obj);
		List activeaddresss2 = dao.getAvailable();
		if (activeaddresss.size() >0 
				&& !dao.getTargetClass().equals(MoneyMove.class)
				&& !dao.getTargetClass().equals(ProductMove.class)) 
					assertTrue("We deactivated it!", Math.abs((activeaddresss.size()-activeaddresss2.size()))==1);
	}
	
	protected void testSingleDAO(AbstractDAO dao) throws Exception{
		assertNotNull(dao);
		BusinessEntity obj = (BusinessEntity) dao.getById(TestConst.TEST_OBJECT_ID);
		assertNotNull(obj);
		assertNotNull(obj.getActive());
		int active = obj.getActive();
		if (active == 0) {
			obj.setActive(1);
		} else {
			obj.setActive(0);
		}
		dao.saveOrUpdate(obj);		
		dao.getById(TestConst.TEST_OBJECT_ID);
		assertFalse("ActiveState must be changed.", obj.getActive() == active);
		
		active = obj.getActive();
		if (active == 0) {
			obj.setActive(1);
		} else {
			obj.setActive(0);
		}
		dao.saveOrUpdate(obj);		
		dao.getById(TestConst.TEST_OBJECT_ID);
		assertFalse("ActiveState must be changed.", obj.getActive() == active);
		
		assertTrue("DAO should return result of proper type", dao.getTargetClass().isInstance(obj));
		obj = (BusinessEntity) dao.getById(TestConst.TEST_OBJECT_ID);
		if (NamedEntity.class.isInstance(obj)){
			List res = dao.searchByName("%", 0, 10);
			assertTrue("We must have instance", res.size()>0);
			assertTrue("No more then 10 results", res.size()<=10);
			assertTrue("DAO must return instances of proper type", dao.getTargetClass().isInstance(res.get(0)));
			System.out.println(obj.getClass().getCanonicalName()+ " is named.");
		} else {
			System.out.println(obj.getClass().getCanonicalName()+ " is not named.");
		}		
		testAvailable(dao);
	}

	public ContragentDAO getContragentDAO() {
		return contragentDAO;
	}

	public void setContragentDAO(ContragentDAO contragentDAO) {
		this.contragentDAO = contragentDAO;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public WarehouseDAO getWarehouseDAO() {
		return warehouseDAO;
	}

	public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
		this.warehouseDAO = warehouseDAO;
	}

	public PriceListDAO getPriceListDAO() {
		return priceListDAO;
	}

	public void setPriceListDAO(PriceListDAO priceListDAO) {
		this.priceListDAO = priceListDAO;
	}

	public PriceDAO getPriceDAO() {
		return priceDAO;
	}

	public void setPriceDAO(PriceDAO priceDAO) {
		this.priceDAO = priceDAO;
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public MoneyMoveDAO getMoneyMoveDAO() {
		return moneyMoveDAO;
	}

	public void setMoneyMoveDAO(MoneyMoveDAO moneyMoveDAO) {
		this.moneyMoveDAO = moneyMoveDAO;
	}

	public ProductMoveDAO getProductMoveDAO() {
		return productMoveDAO;
	}

	public void setProductMoveDAO(ProductMoveDAO productMoveDAO) {
		this.productMoveDAO = productMoveDAO;
	}
}