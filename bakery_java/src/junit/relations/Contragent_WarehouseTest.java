package junit.relations;
import static junit.framework.Assert.*;
import junit.util.TestHelper;

import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.accounting.Contragent;
import org.bakery.server.domain.production.Warehouse;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Contragent_WarehouseTest extends AbstractDBTest {
	private Session session;
	private Transaction tx;
	/**
	 * Contragent (1..1) <--> Warehouse (0..1+)
	 * @throws Exception
	 */
	public void testContragentWarehouse() throws Exception {	
		System.out.println("Test: Contragent (1..1) <--> Warehouse (0..1+) ");
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			Contragent contr = (Contragent) session.get(Contragent.class, 1L);				
			int accBefore = contr.getWarehouses().size();
			Address a1 = (Address) session.get(Address.class, 1L);
			Warehouse w1 = new Warehouse(TestHelper.generateRandomName(), contr, a1);
			Warehouse w2 = new Warehouse(TestHelper.generateRandomName(), contr, a1);
			contr.addWarehouse(w1);
			contr.addWarehouse(w2);
			int contragentsAfter = contr.getWarehouses().size();
			assertTrue("We have added 2 elements!", (contragentsAfter-accBefore)==2);
			session.saveOrUpdate(contr);
		tx.commit();
		session.close();
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			contr = (Contragent) session.get(Contragent.class, 1L);
			contragentsAfter = contr.getWarehouses().size();		
			assertTrue("We have added 2 elements!", (contragentsAfter-accBefore)==2);
		tx.commit();
		session.close();
	}
}
