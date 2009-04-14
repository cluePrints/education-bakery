package org.bakery.server.controllers.beans;

import org.bakery.server.domain.pricing.PriceList;

public class PriceListFormBean extends AbstractFormBean{
	public PriceListFormBean() {
		super.setFormBean(new PriceList());
	}
}