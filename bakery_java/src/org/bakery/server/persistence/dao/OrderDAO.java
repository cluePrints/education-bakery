package org.bakery.server.persistence.dao;

import java.util.List;

import org.bakery.server.domain.accounting.Account;
import org.bakery.server.persistence.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.Session;

public class OrderDAO extends AbstractDAO {
	private String queryGetAccountsAvailableForOrder;
	public List<Account> getAccountsAvailableForOrder(Long orderId) throws Exception {
		List<Account> result = null;
		Session session = getSessionFactory().openSession();
		try {
			Query query = session.createQuery(queryGetAccountsAvailableForOrder);
			query.setLong("searchId", orderId);
			result = query.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.close();
		}
		return result;
	}
	public String getQueryGetAccountsAvailableForOrder() {
		return queryGetAccountsAvailableForOrder;
	}
	public void setQueryGetAccountsAvailableForOrder(
			String queryGetAccountsAvailableForOrder) {
		this.queryGetAccountsAvailableForOrder = queryGetAccountsAvailableForOrder;
	}
}
