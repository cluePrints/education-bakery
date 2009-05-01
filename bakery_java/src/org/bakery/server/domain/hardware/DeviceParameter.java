package org.bakery.server.domain.hardware;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.domain.production.Unit;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.FieldName;
/**
 * Represents concrete parameter of specific device.
 * For example: fridge1_temperature
 * @author Ivan_Sobolev1
 *
 */
public class DeviceParameter extends BusinessEntity implements NamedEntity {
	private static final long serialVersionUID=1L;
	private String name;
	private Unit unit;
	private Device device;
	private int changable;
	private int minimize;
	private Double bestValue;

	@CouldNotBeEmpty()
	@FieldName(name="имя")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@CouldNotBeEmpty()
	@FieldName(name="единица измерения")
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public int getChangable() {
		return changable;
	}
	public void setChangable(int changable) {
		this.changable = changable;
	}
	public int getMinimize() {
		return minimize;
	}
	public void setMinimize(int minimize) {
		this.minimize = minimize;
	}
	
	@CouldNotBeEmpty()
	@FieldName(name="единица измерения")
	public Double getBestValue() {
		return bestValue;
	}
	public void setBestValue(Double bestValue) {
		this.bestValue = bestValue;
	}
}
