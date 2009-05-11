package org.bakery.server.domain.production;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.hardware.DeviceParameter;
import org.bakery.server.validation.CouldNotBeEmpty;
/**
 * Represents effect formula part
 *
 * @author Ivan_Sobolev1
 *
 */
public class RecipeEffectPart extends BusinessEntity{
	private static final long serialVersionUID=1L;
	/**
	 * Parent recipe effect
	 */
	private RecipeEffect recipeEffect;
	
	/**
	 * Multiplicator 
	 */
	private Double multiplicator;
	
	/**
	 * Parameter, to which this effect part is connected to. Null if 
	 * this effect part represents linear part of formula.
	 */
	private DeviceParameter parameter;

	public RecipeEffect getRecipeEffect() {
		return recipeEffect;
	}

	public void setRecipeEffect(RecipeEffect recipeEffect) {
		this.recipeEffect = recipeEffect;
	}

	@CouldNotBeEmpty(message="Значение множителя должно быть задано.")
	public Double getMultiplicator() {
		return multiplicator;
	}

	public void setMultiplicator(Double multiplicator) {
		this.multiplicator = multiplicator;
	}

	public DeviceParameter getParameter() {
		return parameter;
	}

	public void setParameter(DeviceParameter parameter) {
		this.parameter = parameter;
	}		
}
