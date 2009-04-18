package junit.relations;
import junit.util.TestHelper;

import org.bakery.server.domain.accounting.Account;
import org.bakery.server.domain.accounting.Contragent;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Contragent_AccountTest extends AbstractDBTest {
	private Session session;
	private Transaction tx;
	/**
	 * Contragent (1..1) <--> Account (0..1+)
	 * @throws Exception
	 */
	public void testContragentAccount() throws Exception {	
		System.out.println("Test: Contragent (1..1) <--> Account (0..1+) ");
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			Contragent contr = (Contragent) session.get(Contragent.class, 1L);				
			int accBefore = contr.getAccounts().size();
			Account a1 = new Account(TestHelper.generateRandomName());
			Account a2 = new Account(TestHelper.generateRandomName());
			Account a3 = new Account(TestHelper.generateRandomName());
			contr.addAccount(a1);
			contr.addAccount(a2);
			contr.addAccount(a3);
			session.saveOrUpdate(contr);
		tx.commit();
		session.close();
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			contr = (Contragent) session.get(Contragent.class, 1L);
			int contragentsAfter = contr.getAccounts().size();		
			assertTrue("We have added 3 elements!", (contragentsAfter-accBefore)==3);
		tx.commit();
		session.close();
	}
}
