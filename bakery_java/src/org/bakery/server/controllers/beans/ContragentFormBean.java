package org.bakery.server.controllers.beans;

import org.bakery.server.domain.accounting.Contragent;
/*
 * Used to express data on form 
 */
public class ContragentFormBean extends AbstractFormBean{
	public ContragentFormBean() {
		super.setFormBean(new Contragent());
	}
}
