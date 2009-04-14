package org.bakery.server.persistence;

import java.rmi.Remote;
import java.util.List;

public interface DAOInterface extends Remote {
	public List getAvailable() throws Exception;
	public Object getById(Long id) throws Exception;
	public List searchByName(String namePattern, Integer startFrom,
			Integer maxResults) throws Exception;
	public void saveOrUpdate(Object obj) throws Exception;
	public void save(Object obj)  throws Exception;

}
