package junit.relations;

import junit.util.TestHelper;

import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.accounting.Contragent;
import org.bakery.server.domain.production.Unit;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Address_ContragentTest extends AbstractDBTest {
	private Session session;
	private Transaction tx;
	public void testUnit() throws Exception {
		System.out.println("Test: Address (1..1) <--> Contragent (0..1+) ");
		
		Unit unit1 = TestHelper.generateRandomUnit();
		assertNull("Id must be null", unit1.getId());
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			session.saveOrUpdate(unit1);				
			assertNotNull("After save it must be gained with id!", unit1.getId());
			
			Unit unit2 = (Unit) session.get(Unit.class, unit1.getId());
			assertSame("Object must have same identity", unit1, unit2);
			
			unit2 = TestHelper.generateRandomUnit();
			assertNotNull(unit2.getName());
			assertFalse(unit2.getName().length()==0);
			assertFalse("They're should not be equal "+unit1.getName()+unit2.getName(), unit1.equals(unit2));
			
			session.saveOrUpdate(unit2);	
			assertFalse("Id must be unique", unit1.getId().equals(unit2.getId()));
		tx.commit();
		session = getSessionFactory().openSession();
	}
	
	/**
	 * Address (1..1) <--> Contragent (0..1+)
	 * @throws Exception
	 */
	public void testAddressContragent() throws Exception {
		System.out.println("Test: Address (1..1) <--> Contragent (0..1+)");
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			Address addr = TestHelper.generateRandomAddress();
			
			assertNull("Id must be null", addr.getId());
			session.saveOrUpdate(addr);
			assertNotNull("After save it must be gained with id!", addr.getId());
		tx.commit();
		session.close();
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			addr = (Address) session.get(Address.class, 1L);	
			int contragentsBefore = addr.getContragents().size();
			Contragent c1 = new Contragent(TestHelper.generateRandomName());
			Contragent c2 = new Contragent(TestHelper.generateRandomName());
			Contragent c3 = new Contragent(TestHelper.generateRandomName());
			addr.addContragent(c1);
			addr.addContragent(c2);
			addr.addContragent(c3);			
			int contragentsAfter = addr.getContragents().size();
			session.saveOrUpdate(addr);
			assertTrue("We have added contragents!"+contragentsBefore+"->"+contragentsAfter, (contragentsAfter-contragentsBefore)==3);
		tx.commit();
		session.close();
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			addr = (Address) session.get(Address.class, 1L);			
			contragentsAfter = addr.getContragents().size();		
			assertTrue("We have added contragents!"+contragentsBefore+"->"+contragentsAfter, (contragentsAfter-contragentsBefore)==3);
		tx.commit();
		session.close();
	}

}
