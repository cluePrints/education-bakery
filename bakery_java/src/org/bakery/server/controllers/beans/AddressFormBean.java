package org.bakery.server.controllers.beans;

import org.bakery.server.domain.accounting.Address;
/*
 * Used to express data on form 
 */
public class AddressFormBean extends AbstractFormBean{
	public AddressFormBean() {
		super.setFormBean(new Address());
	}
}
