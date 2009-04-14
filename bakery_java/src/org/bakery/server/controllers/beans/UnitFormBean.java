package org.bakery.server.controllers.beans;

import org.bakery.server.domain.production.Unit;
/*
 * Used to express data on form 
 */
public class UnitFormBean extends AbstractFormBean{
	public UnitFormBean() {
		super.setFormBean(new Unit());
	}
}
