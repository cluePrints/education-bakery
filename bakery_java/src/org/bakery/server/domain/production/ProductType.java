package org.bakery.server.domain.production;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.validation.CouldNotBeEmpty;

public class ProductType extends BusinessEntity implements NamedEntity {
	private static final long serialVersionUID=1L;
	private String name;
	private Unit unit = new Unit();
		
	@Override
	public String toString() {
		return name;
	}
	
	@CouldNotBeEmpty(message="������������ �������� ������ ���� ������.")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@CouldNotBeEmpty(message="������� ��������� ���������� �������� ������ ���� �������.")
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}	
}
