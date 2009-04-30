package org.bakery.server.domain.accounting;

import java.util.HashSet;
import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.production.Warehouse;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.FieldName;

public class Contragent extends BusinessEntity {
	private static final long serialVersionUID=1L;
	private String name;
	private Boolean child=false;
	private Address address=new Address(); 
	
	/**
	 * (1..1) Contragent may have (0..many) Accounts
	 */
	private Set<Account> accounts = new HashSet<Account>();
	
	/**
	 * (1..1) Contragent may have (0..many) Warehouses
	 */
	private Set<Warehouse> warehouses = new HashSet<Warehouse>();
	
	public Contragent(String name) {
		this();
		this.name = name;
	}

	public Contragent() {
		super();	
	}

	@Override
	public String toString() {
		return name;
	}

	public void addAccount(Account acc){
		if (acc == null)
			throw new IllegalArgumentException();
		if (acc.getOwner() != null)
			acc.getOwner().accounts.remove(acc);
		acc.setOwner(this);
		accounts.add(acc);			
	}
	
	public void addWarehouse(Warehouse warehouse) {
		if (warehouse == null)
			throw new IllegalArgumentException();
		if (warehouse.getOwner() != null)
			warehouse.getOwner().getWarehouses().remove(warehouse);
		warehouse.setOwner(this);
		warehouses.add(warehouse);
	}
	
	@CouldNotBeEmpty()
	@FieldName(name="имя")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean isChild() {
		return child;
	}
	public void setChild(Boolean child) {
		this.child = child;
	}
	
	
	@CouldNotBeEmpty()
	@FieldName(name="адресс")
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Set<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
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
		final Contragent other = (Contragent) obj;
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

	public Boolean getChild() {
		return child;
	}
}
