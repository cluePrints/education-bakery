package org.bakery.server.controllers.beans;

import org.bakery.server.domain.log.ProductMove;

public class ProductMoveFormBean extends AbstractFormBean{
	public ProductMoveFormBean() {
		super.setFormBean(new ProductMove());
	}
}