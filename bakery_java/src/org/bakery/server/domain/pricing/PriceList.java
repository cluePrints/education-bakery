package org.bakery.server.domain.pricing;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.accounting.Contragent;
import org.bakery.server.validation.CouldNotBeEmpty;
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
	
	@CouldNotBeEmpty(message="Дата начала действия прайс-листа должна быть задана.")
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

	@CouldNotBeEmpty(message="Контрагент, предоставивший прайс-лист должен быть указан.")
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
