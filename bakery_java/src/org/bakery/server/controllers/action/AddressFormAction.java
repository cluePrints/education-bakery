package org.bakery.server.controllers.action;

import org.bakery.server.persistence.dao.UnitDAO;


/**
 * @author Mediaspectrum, Inc.
 */
public class AddressFormAction extends AbstractFormAction{
	public static final String AN_DEFAULT_LIST = "addresses";
	public AddressFormAction() {
		super.setListAttributeName(AN_DEFAULT_LIST);
	}
}


