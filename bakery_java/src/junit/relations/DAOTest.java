package junit.relations;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import junit.util.TestConst;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.log.MoneyMove;
import org.bakery.server.domain.log.ProductMove;
import org.bakery.server.domain.production.Warehouse;
import org.bakery.server.persistence.AbstractDAO;
import org.bakery.server.persistence.DAOFacade;

public class DAOTest extends AbstractSpringTest{
	protected DAOFacade facade;	

	public void testDAOs() throws Exception {
		
		/* Lazy init tests*/
		assertNotNull(((Warehouse) facade.getWarehouseDAO().getAvailable().get(0)).getOwner().getName());
		assertNotNull(((MoneyMove) facade.getMoneyMoveDAO().getAvailable().get(1)).getSourceAccount().getOwner().getName());
		
		BeanInfo info = Introspector.getBeanInfo(facade.getClass(), Object.class);
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		for (PropertyDescriptor propDesc : descriptors){
			if (AbstractDAO.class.isAssignableFrom(propDesc.getPropertyType())){
				Method readMethod = propDesc.getReadMethod();
				AbstractDAO dao = (AbstractDAO) readMethod.invoke(facade);
				testSingleDAO(dao);
			}
		}
		
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
}