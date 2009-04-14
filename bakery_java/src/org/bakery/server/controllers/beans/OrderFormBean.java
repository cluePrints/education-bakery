package org.bakery.server.controllers.beans;

import org.bakery.server.domain.log.Order;

public class OrderFormBean extends AbstractFormBean{
	public OrderFormBean() {
		super.setFormBean(new Order());
	}
}