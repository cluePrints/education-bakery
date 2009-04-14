package org.bakery.server.domain.hardware;

import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.springframework.validation.Errors;
/**
 * Represents concrete device, with it's concrete parameters 
 * 
 * @author Ivan_Sobolev1
 *
 */
public class Device extends BusinessEntity implements NamedEntity {	
	private String name;
	private String description;
	
	private Set<DeviceParameter> parameters; 
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<DeviceParameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<DeviceParameter> parameters) {
		this.parameters = parameters;
	}
}
