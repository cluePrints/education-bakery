package junit.relations;
import junit.util.TestHelper;
import static junit.framework.Assert.*;

import org.bakery.server.domain.pricing.PriceList;
import org.bakery.server.domain.pricing.PriceListItem;
import org.bakery.server.domain.production.ProductType;
import org.bakery.server.domain.production.Unit;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class PriceList_PriceListItem extends AbstractDBTest {
	private Session session;
	private Transaction tx;
	/**
	 * PriceList (1..1) <--> PriceListItem (0..1+)
	 * @throws Exception
	 */
	public void testContragentWarehouse() throws Exception {	
		System.out.println("Test: PriceList (1..1) <--> PriceListItem (0..1+) ");
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			Unit u1 = (Unit) session.get(Unit.class, 1L);
			
			ProductType pt1 = new ProductType();
			pt1.setUnit(u1);
			pt1.setName(TestHelper.generateRandomName());
			session.save(pt1);
			
			ProductType pt2 = new ProductType();
			pt2.setUnit(u1);
			pt2.setName(TestHelper.generateRandomName());
			session.save(pt2);
			
			
			PriceListItem pli1 = new PriceListItem();
			pli1.setPrice(10F);
			pli1.setProduct(pt1);	
			
			PriceListItem pli2 = new PriceListItem();
			pli2.setPrice(20F);
			pli2.setProduct(pt2);	
			
			PriceList pl1 = (PriceList) session.get(PriceList.class, 1L);
			int numBefore = pl1.getItems().size();
			pl1.addItem(pli1);
			pl1.addItem(pli2);
			
			session.saveOrUpdate(pl1);
		tx.commit();
		session.close();
		
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
			pl1 = (PriceList) session.get(PriceList.class, 1L);
			int numAfter = pl1.getItems().size();
			System.out.println(numBefore+"-->"+numAfter);
			assertTrue("We have added 2 elements!", (numAfter-numBefore)==2);
		tx.commit();
		session.close();
	}
}
