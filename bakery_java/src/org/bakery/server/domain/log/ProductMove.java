package org.bakery.server.domain.log;

import java.util.Date;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.production.Warehouse;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.FieldName;
/**
 * Is representation of move between two warehouses. 
 * Product is moved as a reaction on some money move.
 *  Move with null date is transient, e.g. just planned 
 * @author Ivan_Sobolev1
 *
 */
public class ProductMove extends BusinessEntity {
	private static final long serialVersionUID=1L;
	private Warehouse sourceWarehouse=new Warehouse();
	private Warehouse destinationWarehouse=new Warehouse();
	private Date date=(Date) NULL_DATE.clone();
	private MoneyMove moneyMove=new MoneyMove();
	@Override
	public String toString() {
		return moneyMove.toString()+sourceWarehouse+"->"+destinationWarehouse;
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
	public Warehouse getSourceWarehouse() {
		return sourceWarehouse;
	}
	public void setSourceWarehouse(Warehouse sourceWarehouse) {
		this.sourceWarehouse = sourceWarehouse;
	}
	public Warehouse getDestinationWarehouse() {
		return destinationWarehouse;
	}
	public void setDestinationWarehouse(Warehouse destinationWarehouse) {
		this.destinationWarehouse = destinationWarehouse;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@CouldNotBeEmpty()
	@FieldName(name="движение денег, предваряющее перевод товара")
	public MoneyMove getMoneyMove() {
		return moneyMove;
	}
	public void setMoneyMove(MoneyMove moneyMove) {
		this.moneyMove = moneyMove;
	}
	public ProductMove() {
		super();
	}
}
