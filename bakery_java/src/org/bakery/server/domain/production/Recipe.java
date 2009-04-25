package org.bakery.server.domain.production;

import java.util.HashSet;
import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.domain.hardware.DeviceParameter;
import org.springframework.validation.Errors;
/**
 * Represents recipe, which have some effects
 * 
 * (1..1) Recipe consists of (1..many) RecipeEffect
 * @author Ivan_Sobolev1
 *
 */
public class Recipe extends BusinessEntity implements NamedEntity {
	private static final long serialVersionUID=1L;
	private String name;
	
	/**
	 * Human readable description of process, for example:
	 * 
	 * 2 x H20 + ... = C2H50H + ...
	 * 
	 */
	private String formula;
	
	/**
	 * Time in milliseconds, consumed by process
	 */
	private long time;
	
	private Set<DeviceParameter> parameters = new HashSet<DeviceParameter>();
	
	private Set<RecipeEffect> effects = new HashSet<RecipeEffect>();
		
	@Override
	protected String generateAdditionalXML() {
		String tmp ="";
		tmp += "<parameters>";
		for (DeviceParameter param : parameters){
			tmp+= "<parameter>";
			tmp+= "<id>"+param.getId()+"</id>";
			tmp+= "</parameter>";
		}
		tmp += "</parameters>";
		return tmp;
	}
	@Override
	public void validate(Errors errors) {
		// TODO Auto-generated method stub

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Set<RecipeEffect> getEffects() {
		return effects;
	}
	public void setEffects(Set<RecipeEffect> effects) {
		this.effects = effects;
	}
	public Set<DeviceParameter> getParameters() {
		return parameters;
	}
	public void setParameters(Set<DeviceParameter> parameters) {
		this.parameters = parameters;
	}
	
}
