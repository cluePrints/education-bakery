package org.bakery.server.controllers.svc;

import org.bakery.server.persistence.DAOFacade;

public interface ISvcController {

	public abstract DAOFacade getDAOFacade();

}