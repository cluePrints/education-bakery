package junit.relations;

import static junit.framework.Assert.*;
import java.util.Date;

import junit.util.TestHelper;

import org.bakery.server.domain.accounting.Account;
import org.bakery.server.domain.accounting.Contragent;
import org.bakery.server.domain.log.MoneyMove;
import org.bakery.server.domain.log.Order;
import org.bakery.server.domain.pricing.PriceList;
import org.bakery.server.domain.pricing.PriceListItem;
import org.bakery.server.domain.production.ProductType;
import org.bakery.server.domain.production.Unit;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class Order_MoneyMove extends AbstractDBTest {
	private Session session;
	private Transaction tx;
	/**
	 * Order (1..1) <--> MoneyMove (0..1+)
	 * @throws Exception
	 */
	public void testAddressContragent() throws Exception {
		System.out.println("Test: Order (1..1) <--> MoneyMove (0..1+) ");
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			Unit u1 = TestHelper.generateRandomUnit();
			session.saveOrUpdate(u1);
			
			ProductType pt1 = new ProductType();
			pt1.setUnit(u1);
			pt1.setName(TestHelper.generateRandomName());
			session.saveOrUpdate(pt1);
			
			
			PriceListItem pli1 = new PriceListItem();
			pli1.setPrice(10F);
			pli1.setProduct(pt1);			
			
			Contragent contr = (Contragent) session.get(Contragent.class, 1L);
			
			Account acc1 = (Account) session.get(Account.class, 1L);
			session.saveOrUpdate(acc1);
			
			PriceList pl1 = new PriceList();
			pl1.setDate(new Date());
			pl1.setComment("Comment");
			pl1.addItem(pli1);
			pl1.setOwner(contr);
			session.saveOrUpdate(pl1);
			
			//Order order = (Order) session.get(Order.class, 1L);
			Order order = new Order();
			
			order.setProvider(contr);
			order.setConsumer(contr);
			order.setCreationDate(new Date());
			assertNotNull(acc1);
			MoneyMove mv1 = new MoneyMove();
			mv1.setAmount(5);
			mv1.setDestinationAccount(acc1);
			assertNotNull(mv1.getDestinationAccount());
			mv1.setSourceAccount(acc1);
			assertNotNull(mv1.getSourceAccount());
			mv1.setPrice(pli1);
			
			MoneyMove mv2 = new MoneyMove();
			mv2.setAmount(3);
			mv2.setDestinationAccount(acc1);
			assertNotNull(acc1.getId());
			assertFalse(acc1.getId()==0);
			assertNotNull(acc1);
			assertNotNull(mv2.getDestinationAccount());
			mv2.setSourceAccount(acc1);
			assertNotNull(mv2.getSourceAccount());
			mv2.setPrice(pli1);
			order.addMoneyMove(mv1);
			order.addMoneyMove(mv2);

			assertNull("Id must be null", order.getId());
			session.saveOrUpdate(order);
			assertNotNull("After save it must be gained with id!", order.getId());
			
			
		tx.commit();
		session.close();
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			order = (Order) session.get(Order.class, 1L);	
			acc1 = (Account) session.get(Account.class, 1L);
			int numBefore = order.getMoneyMoves().size();
			mv1 = new MoneyMove();
			mv1.setAmount(3);
			mv1.setDestinationAccount(acc1);
			assertNotNull(mv1.getDestinationAccount());
			mv1.setSourceAccount(acc1);
			assertNotNull(mv1.getSourceAccount());
			mv1.setPrice(pli1);
			
			mv2 = new MoneyMove();
			mv2.setAmount(9);
			mv2.setDestinationAccount(acc1);
			assertNotNull(mv2.getDestinationAccount());
			mv2.setSourceAccount(acc1);
			assertNotNull(mv2.getSourceAccount());
			mv2.setPrice(pli1);
			
			order.addMoneyMove(mv1);
			order.addMoneyMove(mv2);
			int numAfter = order.getMoneyMoves().size();
			session.saveOrUpdate(order);
			assertTrue("We have added "+numBefore+"->"+numAfter, (numAfter-numBefore)==2);
		tx.commit();
		session.close();
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
		order = (Order) session.get(Order.class, 1L);			
			numAfter = order.getMoneyMoves().size();		
			assertTrue("We have added "+numBefore+"->"+numAfter, (numAfter-numBefore)==2);
		tx.commit();
		session.close();
	}

}
