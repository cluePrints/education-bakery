package org.bakery.server.persistence;

import java.rmi.Remote;
import java.util.List;

import org.bakery.server.domain.BusinessEntity;

public interface DAOInterface extends Remote {
	public List<BusinessEntity> getAvailable() throws Exception;
	public Object getById(Long id) throws Exception;
	public List<BusinessEntity> searchByName(String namePattern, Integer startFrom,
			Integer maxResults) throws Exception;
	public void saveOrUpdate(Object obj) throws Exception;
	public void save(Object obj)  throws Exception;

}
