package org.bakery.server.domain.accounting;

import org.bakery.server.controllers.validation.CommonFormValidator;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.springframework.validation.Errors;
/**
 * Represents Account entity
 * 
 * @author Ivan_Sobolev1
 *
 */
public class Account extends BusinessEntity implements NamedEntity{
	private static final long serialVersionUID=1L;
	@Override
	public void validate(Errors errors) { 
		if (CommonFormValidator.isEmptyEntity(owner))
			errors.reject(null, "account.owner.empty");
	}
	
	private String name;
	private String desc;
	private Contragent owner = new Contragent();

	public Account() {
		super();
	}
	public Account(String name) {
		this();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return owner.getName()+"."+name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Contragent getOwner() {
		return owner;
	}
	public void setOwner(Contragent owner) {
		this.owner = owner;
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
		final Account other = (Account) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
