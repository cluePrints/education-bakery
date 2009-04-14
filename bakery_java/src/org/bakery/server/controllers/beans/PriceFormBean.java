package org.bakery.server.controllers.beans;

import org.bakery.server.domain.pricing.PriceListItem;


public class PriceFormBean extends AbstractFormBean{
	public PriceFormBean() {
		super.setFormBean(new PriceListItem());
	}
}