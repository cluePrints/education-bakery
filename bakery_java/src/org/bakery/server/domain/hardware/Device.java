package org.bakery.server.domain.hardware;

import java.util.Set;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.domain.NamedEntity;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.FieldName;
/**
 * Represents concrete device, with it's concrete parameters 
 * 
 * @author Ivan_Sobolev1
 *
 */
public class Device extends BusinessEntity implements NamedEntity {
	private static final long serialVersionUID=1L;
	private String name;
	private String description;
	
	private Set<DeviceParameter> parameters; 
	public String getName() {
		return name;
	}

	@CouldNotBeEmpty()
	@FieldName(name="наименование")
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
