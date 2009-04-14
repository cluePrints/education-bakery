package org.bakery.server.domain.log;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bakery.server.controllers.validation.CommonFormValidator;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.accounting.Account;
import org.bakery.server.domain.pricing.PriceListItem;
import org.springframework.validation.Errors;
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
	private float amount;
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
	public void validate(Errors errors) {
		if (CommonFormValidator.isEmptyEntity(sourceAccount))
			errors.reject(null, "moneyMove.sourceAccount.empty");
		if (CommonFormValidator.isEmptyEntity(destinationAccount))
			errors.reject(null, "moneyMove.destinationAccount.empty");
		if (CommonFormValidator.isEmptyEntity(order))
			errors.reject(null, "moneyMove.order.empty");
		if (CommonFormValidator.isEmptyEntity(price))
			errors.reject(null, "moneyMove.price.empty");
	}

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
	
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
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
	public Account getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	public Account getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(Account sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public MoneyMove() {
		super();
	}
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
