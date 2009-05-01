package org.bakery.server.domain.production;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.validation.CouldNotBeEmpty;
/**
 * Represents effect, which is part of a recipe
 * 
 * (1..1) Recipe consists of (1..many) RecipeEffect
 * @author Ivan_Sobolev1
 *
 */
public class RecipeEffect extends BusinessEntity{
	private static final long serialVersionUID=1L;
	/**
	 * Parent recipe
	 */
	private Recipe recipe;
	
	/**
	 * Is it consumed, or generated effect
	 */
	private int consumed=0;
	
	/**
	 * Expression to calculate amount, dependent on parameters
	 * 
	 * for example "2*{P1} + 4*{E19}", 1 & 19 are id's of dependent parameters and effects
	 */
	private String resultFormula;
	
	/**
	 * Product type, used, or produced
	 */
	private ProductType productType;
	
	@CouldNotBeEmpty(message="Рецепт, к которому относится этот потребляемый\\производимый эффект должен быть указан.")
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public int getConsumed() {
		return consumed;
	}
	public void setConsumed(int consumed) {
		this.consumed = consumed;
	}
	
	@CouldNotBeEmpty(message="Формула вычисления результата должна быть задана.")
	public String getResultFormula() {
		return resultFormula;
	}
	public void setResultFormula(String resultFormula) {
		this.resultFormula = resultFormula;
	}
	
	@CouldNotBeEmpty(message="Должен быть указан потребляемый\\производимый тип продукта.")
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
}
