package org.bakery.server.domain.accounting;

import java.util.HashSet;
import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.domain.production.Warehouse;
import org.springframework.validation.Errors;

/**
 * Address representation and linked concerns, like 
 * 	(0..) Contragent <-> (1..1) Address
 *  (0..) Warehouse	 <-> (1..1) Address
 *  
 * @author Ivan_Sobolev1
 *
 */
public class Address extends BusinessEntity implements NamedEntity {
	private static final long serialVersionUID=1L;
	@Override
	public void validate(Errors errors) {
		
	}

	/**
	 * Address string, for example, 'One tree hill str., 3'
	 */
	private String name;
	
	/**
	 * Contragents, that have this address as registration address;
	 * (1..1) Address may have (0..many) Contragents
	 */
	private Set<Contragent> contragents = new HashSet<Contragent>();
		
	/**
	 * (1..1) Contragent may have (0..many) Warehouses
	 */
	private Set<Warehouse> warehouses = new HashSet<Warehouse>();

	public Address(String address) {
		this();
		this.name = address;
	}

	public Address() {
		super();
	}
	
	
	
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Modifies both sides of bidirectional relationship between Adress and Contragents
	 * 
	 * @param contragent
	 */
	public void addContragent(Contragent contragent) {
		if (contragent == null)
			throw new IllegalArgumentException();
		if (contragent.getAddress() != null)
			contragent.getAddress().contragents.remove(contragent);
		contragent.setAddress(this);
		contragents.add(contragent);
	}
	
	public void addWarehouse(Warehouse warehouse) {
		if (warehouse == null)
			throw new IllegalArgumentException();
		if (warehouse.getAddress() != null)
			warehouse.getAddress().getWarehouses().remove(warehouse);
		warehouse.setAddress(this);
		warehouses.add(warehouse);
	}
	

	public Set<Contragent> getContragents() {
		return contragents;
	}

	public void setContragents(Set<Contragent> contragents) {
		this.contragents = contragents;
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
		final Address other = (Address) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Set<Warehouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(Set<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
