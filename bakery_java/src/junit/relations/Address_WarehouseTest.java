package junit.relations;
import junit.util.TestHelper;

import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.accounting.Contragent;
import org.bakery.server.domain.production.Warehouse;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Address_WarehouseTest extends AbstractDBTest {
	private Session session;
	private Transaction tx;
	/**
	 * Address (1..1) <--> Warehouse (0..1+)
	 * @throws Exception
	 */
	public void testContragentAccount() throws Exception {	
		System.out.println("Test: Address (1..1) <--> Warehouse (0..1+) ");
		
		session = getSessionFactory().openSession();
		Long id = null;
		tx = session.beginTransaction();
			Address addr = (Address) session.get(Address.class, 1L);			
			int accBefore = addr.getWarehouses().size();
			Contragent c1 = new Contragent(TestHelper.generateRandomName());
			addr.addContragent(c1);
			Warehouse w1 = new Warehouse(TestHelper.generateRandomName(), c1, addr);
			Warehouse w2 = new Warehouse(TestHelper.generateRandomName(), c1, addr);
			addr.addWarehouse(w1);
			addr.addWarehouse(w2);
			System.out.print("AAAAAAAAAAAAA" +addr.getContragents().contains(c1));
			session.saveOrUpdate(addr);
		tx.commit();
		id = c1.getId();
		assertNotNull(id);
		session.close();
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			addr = (Address) session.get(Address.class, 1L);	
			int contragentsAfter = addr.getWarehouses().size();		
			assertTrue("We have added 2 elements!", (contragentsAfter-accBefore)==2);
		tx.commit();
		session.close();
	}
}
