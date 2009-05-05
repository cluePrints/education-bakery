package org.bakery.server.domain;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bakery.server.controllers.svc.helper.SvcHelper;
import org.bakery.server.validation.CouldNotBeEmpty;
import org.bakery.server.validation.ValidationHelper;

/**
 * Abstract parent for all entities from domain. All objects have:
 * 	- id unique within their DB
 *  - activestate
 *   
 * @author Ivan_Sobolev1
 *
 */
public abstract class BusinessEntity implements Serializable {
	private static final long serialVersionUID=1L;
	protected static final HashMap<String, String> NO_ERRORS = new HashMap<String, String>(0); 
	private Long id=NOT_EXISTENT;
	
	private int active=1;
		
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	
	public String getTextView(){
		return toString();
	}
	
	public static final Date NULL_DATE = new Date(0){	
		private static final long serialVersionUID = 1L;
		public boolean equals(Date d) {
			if (d==null) return false;
			return this.getTime() == d.getTime();
		}
		public String toString(){
			if (this.getTime() == 0) {
				return " ";
			} else {
				return super.toString();
			}
		}	
	};	
	
	public String toXml() throws IntrospectionException, InvocationTargetException, IllegalAccessException{
		final String G=">";
		final String L="<";
		final String LE="</";
		final String SHORT = "   ";
		final String LONG = "      ";
		StringBuilder tmp = new StringBuilder();
		Class clazz = this.getClass();
		String classInstanceName = clazz.getSimpleName().toLowerCase();
		tmp.append("\n").append(SHORT).append(L).append(classInstanceName).append(G);
		BeanInfo inf = java.beans.Introspector.getBeanInfo(clazz, Object.class);
		PropertyDescriptor[] propDescriptors = inf.getPropertyDescriptors();
		for (PropertyDescriptor prop : propDescriptors) {			
			String propName = prop.getName();
			Method readMethod = prop.getReadMethod();
			Object result = readMethod.invoke(this);
			tmp.append("\n").append(LONG).append(L).append(propName).append(G);	//<paramName>			
			if (result == null) {
				// append nothing
			} else if (result instanceof BusinessEntity) {
				tmp.append(((BusinessEntity)result).getId());
			} else if (result.getClass().isPrimitive()){				
				tmp.append(result);
			} else if (result instanceof Integer
					|| result instanceof Short
					|| result instanceof Byte
					|| result instanceof Long
					|| result instanceof Float
					|| result instanceof Double
					|| result instanceof Boolean){
				tmp.append(result);
			} else if (result instanceof String){
				tmp.append(SvcHelper.replaceXMLDeclinedCharacters((String) result));
			} else if (result instanceof Date){
				tmp.append(SvcHelper.dateToString((Date) result));
			}
			tmp.append(LE).append(propName).append(G);  //</paramName>			
		}
		tmp.append(generateAdditionalXML());
		tmp.append("\n").append(SHORT).append(LE).append(classInstanceName).append(G);
		return tmp.toString();		
	};
	
	/**
	 * Generate additional xml fields. Supposed to be called right before closing entity tag
	 * @return
	 */
	protected String generateAdditionalXML(){
		return "";
	}
	
	private static final Long NOT_EXISTENT=null;
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BusinessEntity other = (BusinessEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

