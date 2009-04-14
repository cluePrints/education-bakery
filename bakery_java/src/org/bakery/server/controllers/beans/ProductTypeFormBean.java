package org.bakery.server.controllers.beans;

import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.production.ProductType;

public class ProductTypeFormBean extends AbstractFormBean {
	public ProductTypeFormBean() {
		super.setFormBean(new ProductType());
	}
}
