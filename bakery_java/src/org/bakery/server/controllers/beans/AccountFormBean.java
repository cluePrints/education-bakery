package org.bakery.server.controllers.beans;

import org.bakery.server.domain.accounting.Account;
/*
 * Used to express data on form 
 */
public class AccountFormBean extends AbstractFormBean{
	public AccountFormBean() {
		super.setFormBean(new Account());
	}
}
