package org.bakery.server.domain.pricing;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bakery.server.controllers.validation.CommonFormValidator;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.accounting.Contragent;
import org.springframework.validation.Errors;
/**
 * Represents set of pricelist items, shipped by some contragent at once
 * @author Ivan_Sobolev1
 *
 */
public class PriceList extends BusinessEntity {
	private static final long serialVersionUID=1L;
	private Date date=(Date) NULL_DATE.clone();
	private String comment;
	private Contragent owner=new Contragent();

	private Set<PriceListItem> items=new HashSet<PriceListItem>();
	
	@Override
	public void validate(Errors errors) {
		if (CommonFormValidator.isEmptyEntity(owner))
			errors.reject(null, "priceList.owner.empty");
	}
	
	public String toString() {
		String result = owner.toString()+", "+date;
		if (comment !=null && comment.trim().length()>0)
			result = result + " ("+comment+")";
		return result;
	}

	public void addItem(PriceListItem item){
		if (item == null)
			throw new IllegalArgumentException();
		if (item.getParent() != null)
			item.getParent().items.remove(item);
		item.setParent(this);
		items.add(item);			
	}
	
	public PriceList() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Contragent getOwner() {
		return owner;
	}

	public void setOwner(Contragent owner) {
		this.owner = owner;
	}

	public Set<PriceListItem> getItems() {
		return items;
	}

	public void setItems(Set<PriceListItem> items) {
		this.items = items;
	}
}
