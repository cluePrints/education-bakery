package org.bakery.server.persistence;

import java.util.List;

import org.bakery.server.domain.BusinessEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

public class AbstractDAO implements DAOInterface {
	private SessionFactory sessionFactory;
	// "from Unit u where u.name like :searchName order by u.name"
	private String queryGetAvailable;
	private String querySearchByName;
	private String targetClassName;
	private Class<BusinessEntity> targetClass;
	private String queryFetchById;
	private String queryFetchByParent;
	
	public List<BusinessEntity> getAvailable() throws Exception{
		List<BusinessEntity> result;
		Session session = getSessionFactory().openSession();
		try {
			Query query = session.createQuery(queryGetAvailable);
			result = query.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.close();
		}
		return result;
	}
	public String getQueryGetAvailable() {
		return queryGetAvailable;
	}
	public void setQueryGetAvailable(String queryGetAvailable) {
		this.queryGetAvailable = queryGetAvailable;
	}
	
	public List<BusinessEntity> searchByExample(Object exampleObj, Integer startFrom,
			Integer maxResults) throws Exception {
		Example sample = Example.create(exampleObj).ignoreCase()
				.excludeZeroes().enableLike(MatchMode.ANYWHERE);
		Session session = sessionFactory.openSession();
		return session.createCriteria(targetClass).add(sample).list();
	}

	/**
	 * Used to fetch from DB by id
	 * 
	 * @param id
	 * @return
	 */
	public Object getById(Long id) throws Exception  {
		Object result;
		Session session = sessionFactory.openSession();
		try {
			result = session.get(targetClass, id);
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.close();
		}
		return result;
	}

	public void removeById(Long id) throws Exception  {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object obj = session.get(targetClass, id);
			if (obj != null) {
				BusinessEntity e = (BusinessEntity) obj;
				e.setActive(0);
				session.saveOrUpdate(e);
			}
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			session.close();
		}
	}

	public void restoreById(Long id) throws Exception  {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object obj = session.get(targetClass, id);
			if (obj != null) {
				BusinessEntity e = (BusinessEntity) obj;
				e.setActive(1);
				session.saveOrUpdate(e);
			}
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			session.close();
		}
	}

	/**
	 * Used to fetch portions of objects from DB according to their name
	 * 
	 * @param namePattern
	 * @param startFrom
	 * @param maxResults
	 * @return
	 */
	public List<BusinessEntity> searchByName(String namePattern, Integer startFrom,
			Integer maxResults) throws Exception {
		List<BusinessEntity> result;
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery(querySearchByName);
			query.setString("searchName", namePattern)
					.setFirstResult(startFrom).setMaxResults(maxResults);
			result = query.list();
		} catch (Exception ex) {
			throw ex;
		} finally {
			session.close();
		}
		return result;
	}

	public void saveOrUpdate(Object obj) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(obj);
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw ex;
		} finally {
			session.close();
		}
	}

	public void save(Object obj)  throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(obj);
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
			throw new RuntimeException(ex);
		} finally {
			session.close();
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getQuerySearchByName() {
		return querySearchByName;
	}

	public void setQuerySearchByName(String querySearchByName) {
		this.querySearchByName = querySearchByName;
	}

	public Class<BusinessEntity> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<BusinessEntity> targetClass) {
		this.targetClass = targetClass;
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public void setTargetClassName(String targetClassName) throws Exception {
		this.targetClassName = targetClassName;
		this.targetClass = (Class<BusinessEntity>) Class.forName(targetClassName);
	}
	public String getQueryFetchById() {
		return queryFetchById;
	}
	public void setQueryFetchById(String queryFetchById) {
		this.queryFetchById = queryFetchById;
	}
	public String getQueryFetchByParent() {
		return queryFetchByParent;
	}
	public void setQueryFetchByParent(String queryFetchByParent) {
		this.queryFetchByParent = queryFetchByParent;
	}
}
