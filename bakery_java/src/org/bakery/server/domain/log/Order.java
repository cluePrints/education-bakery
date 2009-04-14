package org.bakery.server.domain.log;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bakery.server.controllers.validation.CommonFormValidator;
import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.accounting.Contragent;
import org.springframework.validation.Errors;

/**
 * Order is representation of future actions between two Contragents.
 * Order has money moves to be made and product moves to be made after that.
 * 	Products are shipped with some prices
 * 	Order may be at states:
 * 		- NEW (Just made persistent at database level but without any transactions)
 * 		- WAIT_PAY (When some of money moves were made)
 * 		- WAIT_SHIPPING (When some of product moves were made)
 * 		- DONE (When all the product and money moves done)
 * 
 * 	(1..1) Order <--> MoneyMove
 *  (1..1) Order <--> (1..*) ProductionPlan
 * 
 * @author Ivan_Sobolev1
 *
 */
public class Order extends BusinessEntity {
	/**
	 * Contragent that will ship all the items from order
	 */
	private Contragent provider=new Contragent();
	
	/**
	 * Contragent that will recieve all items from order and pay
	 */
	private Contragent consumer=new Contragent();
	
	/**
	 * Date of order creation
	 */
	private Date creationDate=(Date) NULL_DATE.clone();
	
	/**
	 * Date when order was done 
	 */
	private Date doneDate=(Date) NULL_DATE.clone();
	
	/**
	 * Money moves, linked with this order. When total money moves processed are equal to the cost of
	 * order, order is 'Fully payed'.
	 */
	private Set<MoneyMove> moneyMoves = new HashSet<MoneyMove>();
	
	
	
	@Override
	public void validate(Errors errors) {
		if (CommonFormValidator.isEmptyEntity(provider))
			errors.reject(null, "order.provider.empty");
		if (CommonFormValidator.isEmptyEntity(consumer))
			errors.reject(null, "order.consumer.empty");
		if (CommonFormValidator.isEmptyEntity(creationDate)) {
			errors.reject(null, "order.creationDate.empty");
		} else {
			if (doneDate != null && !doneDate.equals(NULL_DATE) && creationDate.after(doneDate)) {
				errors.reject(null, "order.doneDate.wrong");
			}
		}
	}

	@Override
	public String toString() {
		String result=provider + "->" + consumer + ", "+creationDate;
		if (NULL_DATE.equals(doneDate)){
			result+="*";
		} else {
			result+=doneDate;
		}
		return result;
	}

	public void addMoneyMove(MoneyMove mv) {
		if (mv == null)
			throw new IllegalArgumentException();
		if (mv.getOrder() != null)
			mv.getOrder().moneyMoves.remove(mv);
		mv.setOrder(this);
		moneyMoves.add(mv);
	}
	
	public Order() {
		super();
	}
	public Contragent getProvider() {
		return provider;
	}
	public void setProvider(Contragent provider) {
		this.provider = provider;
	}
	public Contragent getConsumer() {
		return consumer;
	}
	public void setConsumer(Contragent consumer) {
		this.consumer = consumer;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getDoneDate() {
		return doneDate;
	}
	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	public Set<MoneyMove> getMoneyMoves() {
		return moneyMoves;
	}
	public void setMoneyMoves(Set<MoneyMove> moneyMoves) {
		this.moneyMoves = moneyMoves;
	}
}
