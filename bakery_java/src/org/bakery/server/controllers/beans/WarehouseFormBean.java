package org.bakery.server.controllers.beans;

import org.bakery.server.domain.production.Warehouse;
/*
 * Used to express data on form 
 */
public class WarehouseFormBean extends AbstractFormBean{
	public WarehouseFormBean() {
		super.setFormBean(new Warehouse());
	}
}
