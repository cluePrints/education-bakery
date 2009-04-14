package org.bakery.server.domain.production;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.springframework.validation.Errors;
/**
 * Measurement unit. Examples: gram, kilogram, cm, etc.
 * 
 * @author Ivan_Sobolev1
 *
 */
public class Unit extends BusinessEntity implements NamedEntity{
	private String name;

	public Unit(String name) {
		this();
		this.name = name;
	}
	
	public Unit(Long id, String name) {
		this();
		this.name = name;
		setId(id);
	}
	
	
	
	@Override
	public void validate(Errors errors) {
		// TODO Auto-generated method stub
		
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

	public Unit() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Unit other = (Unit) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
