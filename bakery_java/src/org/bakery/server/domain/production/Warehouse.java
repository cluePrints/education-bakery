package org.bakery.server.domain.production;

import org.bakery.server.controllers.validation.CommonFormValidator;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.accounting.Contragent;
import org.springframework.validation.Errors;

public class Warehouse extends BusinessEntity implements NamedEntity{
	private String name;
	private Contragent owner=new Contragent();
	private Address address=new Address();
	public Warehouse() {
		super();
	}
	public Warehouse(String name) {
		this();
		this.name = name;
	}	
	
	@Override
	public void validate(Errors errors) {
		if (CommonFormValidator.isEmptyEntity(owner))
			errors.reject(null, "warehouse.owner.empty");
		if (CommonFormValidator.isEmptyEntity(address))
			errors.reject(null, "warehouse.address.empty");
	}
	
	@Override
	public String toString() {
		return owner+"."+name+" ("+address+")";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Contragent getOwner() {
		return owner;
	}
	public void setOwner(Contragent owner) {
		this.owner = owner;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
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
		final Warehouse other = (Warehouse) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Warehouse(String name, Contragent owner, Address address) {
		super();
		this.name = name;
		this.owner = owner;
		this.address = address;
	}
}
