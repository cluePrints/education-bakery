package org.bakery.server.domain.production;

import org.bakery.server.domain.BusinessEntity;
import org.springframework.validation.Errors;
/**
 * Represents effect, which is part of a recipe
 * 
 * (1..1) Recipe consists of (1..many) RecipeEffect
 * @author Ivan_Sobolev1
 *
 */
public class RecipeEffect extends BusinessEntity{
	/**
	 * Parent recipe
	 */
	private Recipe recipe;
	
	/**
	 * Is it consumed, or generated effect
	 */
	private int consumed;
	
	/**
	 * Expression to calculate amount, dependent on parameters
	 * 
	 * for example "2*{1} + 4*{19}", 1 & 19 are id's of dependent parameters
	 */
	private String resultFormula;
	
	/**
	 * Product type, used, or produced
	 */
	private ProductType productType;
	
	/**
	 * Amount used/produced
	 */
	private float amount;
	@Override
	public void validate(Errors errors) {
		// TODO Auto-generated method stub
		
	}
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
	public String getResultFormula() {
		return resultFormula;
	}
	public void setResultFormula(String resultFormula) {
		this.resultFormula = resultFormula;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

}
