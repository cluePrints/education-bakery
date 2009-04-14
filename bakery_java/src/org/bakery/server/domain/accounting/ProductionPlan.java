package org.bakery.server.domain.accounting;

import java.util.Date;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.hardware.Device;
import org.bakery.server.domain.log.Order;
import org.springframework.validation.Errors;
/**
 * This entity links together and devices used to produce something.
 * Order is expected to be holder of plans at entity level.
 * 
 * (1..1) Order contains (1..*) ProductionPlan
 * 
 * Also it helps to plan device load.
 * @author Ivan_Sobolev1
 *
 */
public class ProductionPlan extends BusinessEntity {
	private Order order;
	
	/**
	 * Used device
	 */
	private Device device;
	
	/**
	 * Start date
	 */
	private Date usageDate=(Date) NULL_DATE.clone();
	@Override
	public void validate(Errors errors) {
		// TODO Auto-generated method stub

	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Date getUsageDate() {
		return usageDate;
	}
	public void setUsageDate(Date usageDate) {
		this.usageDate = usageDate;
	}

}
