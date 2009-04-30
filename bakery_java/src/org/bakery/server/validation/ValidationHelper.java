package org.bakery.server.validation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class ValidationHelper {
	public static String getFieldReadableName(PropertyDescriptor desc){
		FieldName f = desc.getReadMethod().getAnnotation(FieldName.class);
		String res;
		try {
			res = f.name();
		} catch (Exception e){
			res = desc.getName();
		}
		return res;
	}
}
