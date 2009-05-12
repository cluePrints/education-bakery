package org.bakery.server.domain.accounting;

import java.util.Date;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.log.Order;
import org.bakery.server.domain.production.Recipe;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.GreaterThen;
/**
 * This entity links together and devices used to produce something.
 * Order is expected to be holder of plans at entity level.
 * 
 * (1..1) Order contains (1..*) ProductionPlan
 * 
 * Also it helps to plan device load.
 * @author Ivan_Sobolev1
 *
 */
public class ProductionPlan extends BusinessEntity {
	private static final long serialVersionUID=1L;
	private Order order;
	
	/**
	 * Used recipe. Device could be recognized by recipe
	 */
	private Recipe recipe;
	
	/**
	 * Start date
	 */
	private Date startDate=null;

	@CouldNotBeEmpty(message="«аказ, к которому относитс€ производственный план не указан.")
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}

	@CouldNotBeEmpty(message="–ецепт приготовлени€ продукта должен быть выбран.")
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	@GreaterThen(message="ѕроизводственный план можно создать только дл€ будущего периода.")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
