package org.bakery.server.controllers.beans;

import org.bakery.server.domain.log.MoneyMove;


public class MoneyMoveFormBean extends AbstractFormBean{
	public MoneyMoveFormBean() {
		super.setFormBean(new MoneyMove());
	}
}