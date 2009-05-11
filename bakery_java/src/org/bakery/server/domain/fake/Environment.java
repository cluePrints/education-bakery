package org.bakery.server.domain.fake;

import java.util.Date;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.validation.CouldNotBeEmpty;

public class Environment extends BusinessEntity {
	public static final long serialVersionUID=1L;
	private Date currDate = new Date();
	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}
}
