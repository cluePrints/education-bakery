package org.bakery.server.domain.hardware;

import java.util.HashSet;
import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.domain.production.Recipe;
import org.bakery.server.domain.production.Unit;
import org.bakery.server.validation.CouldNotBeEmpty;
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
	
	private Set<Recipe> recipes = new HashSet<Recipe>();

	@CouldNotBeEmpty(message="Название параметра устройства должно быть задано.")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@CouldNotBeEmpty(message="Единица измерения для параметра устройства не указана.")
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
	
	@CouldNotBeEmpty(message="Рекомендуемое значение параметра устройства должно быть задано")
	public Double getBestValue() {
		return bestValue;
	}
	public void setBestValue(Double bestValue) {
		this.bestValue = bestValue;
	}
	public Set<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}
}
