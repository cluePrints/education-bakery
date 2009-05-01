package org.bakery.server.domain.log;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.accounting.Account;
import org.bakery.server.domain.pricing.PriceListItem;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.FieldName;
/**
 * Abstractes money move between two accounts. Is result of 
 * 	Order processing.
 * 	Move without date is planned
 * 
 * Product moves could be made according to planned or processed money moves.
 * 
 * @author Ivan_Sobolev1
 *
 */
public class MoneyMove extends BusinessEntity {
	private static final long serialVersionUID=1L;
	private Double amount;
	private String desc;
	private Date date=(Date) NULL_DATE.clone();
	private Account destinationAccount=new Account();
	private Account sourceAccount=new Account();
	private Order order=new Order();
	private PriceListItem price=new PriceListItem();
	
	/**
	 * Product moves, linked with this order. When all the product moves are done, order is 'Fully shipped' 
	 */
	private Set<ProductMove> productMoves=new HashSet<ProductMove>();	
	
	
	@Override
	public String toString() {		
		String result = order.toString() + ", "  + price.getProduct().getName()
				+ "(" + amount + "x"+ price.getPrice() + ")"
				+", "+destinationAccount+"->"+sourceAccount;
		return result;
	}

	@Override
	public void setActive(int active) {
		super.setActive(active);
		if (active<=0) {
			this.date = new Date();
		} else {			
			this.date = (Date) NULL_DATE.clone();
		}
	}
	
	@Override
	public int getActive() {
		return (NULL_DATE.equals(this.date) || date == null) ? 1 : 0;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@CouldNotBeEmpty()
	@FieldName(name="денежный счет назначения")
	public Account getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	@CouldNotBeEmpty()
	@FieldName(name="денежный счет источник")
	public Account getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(Account sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	
	@CouldNotBeEmpty()
	@FieldName(name="заказ")
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public MoneyMove() {
		super();
	}
	
	@CouldNotBeEmpty()
	@FieldName(name="прайс-лист")
	public PriceListItem getPrice() {
		return price;
	}
	public void setPrice(PriceListItem price) {
		this.price = price;
	}
	public Set<ProductMove> getProductMoves() {
		return productMoves;
	}
	public void setProductMoves(Set<ProductMove> productMoves) {
		this.productMoves = productMoves;
	}
}
