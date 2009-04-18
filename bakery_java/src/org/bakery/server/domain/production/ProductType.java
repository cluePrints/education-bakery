package org.bakery.server.domain.production;

import org.bakery.server.controllers.validation.CommonFormValidator;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.springframework.validation.Errors;

public class ProductType extends BusinessEntity implements NamedEntity {
	private static final long serialVersionUID=1L;
	private String name;
	private Unit unit = new Unit();
	
	@Override
	public void validate(Errors errors) {
		if (CommonFormValidator.isEmptyEntity(unit))
			errors.reject(null, "productType.unit.empty");		
	}
		
	@Override
	public String toString() {
		return name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}	
}
