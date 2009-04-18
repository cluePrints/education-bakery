package org.bakery.server.persistence;

import java.util.List;

import org.bakery.server.domain.production.ProductType;
import org.bakery.server.domain.production.Unit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SearchEngine {
	private SessionFactory sessionFactory;
	
	public List<Unit> getUnitsByName(String namePattern, int startFrom, int maxNum) {
		List<Unit> result = null;
		Session session = sessionFactory.openSession();
			
			Query query = session.createQuery("from Unit u where u.name like :searchName order by u.name");
			query.setString("searchName", namePattern)
				.setFirstResult(startFrom)
				.setMaxResults(maxNum);
			result = query.list();
		session.close();
		return result;
	}
	
	public List<ProductType> getProductTypesByName(String namePattern, int startFrom, int maxNum) {
		List<ProductType> result = null;
		Session session = sessionFactory.openSession();
			
			Query query = session.createQuery("from ProductType t where t.name like :searchName order by t.name");
			query.setString("searchName", namePattern)
				.setFirstResult(startFrom)
				.setMaxResults(maxNum);
			result = query.list();
		session.close();
		return result;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
