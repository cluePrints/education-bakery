package org.bakery.server.persistence.dao;

import java.util.List;

import org.bakery.server.domain.log.MoneyMove;
import org.bakery.server.persistence.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.Session;

public class MoneyMoveDAO extends AbstractDAO {
	private String queryFetchByParent;
	public List<MoneyMove> fetchByParentId(Long id) throws Exception{
		List<MoneyMove> result;
		Session session = getSessionFactory().openSession();
		try {
			Query query = session.createQuery(queryFetchByParent);
			query.setLong("searchId", id);			
			result = query.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.close();
		}
		return result;

	}
	public String getQueryFetchByParent() {
		return queryFetchByParent;
	}
	public void setQueryFetchByParent(String queryFetchByParent) {
		this.queryFetchByParent = queryFetchByParent;
	}
}
