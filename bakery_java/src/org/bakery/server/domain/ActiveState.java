package org.bakery.server.domain;

import java.io.Serializable;

/**
 * State used to allow deactivate some business objects, but allow them to be 
 * used/linked by other entities.
 * 
 * @author Ivan_Sobolev1
 *
 */
public class ActiveState implements Serializable{
	private static final long serialVersionUID=1L;
	private Integer value=1;

	private ActiveState() {
		super();
	}

	public ActiveState(int value) {
		this();
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}	
}
