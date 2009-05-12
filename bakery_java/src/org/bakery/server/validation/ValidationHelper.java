package org.bakery.server.validation;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

import org.bakery.server.domain.BusinessEntity;

public class ValidationHelper {
	public static void validateEmpty(Object target, PropertyDescriptor prop,
			Map<String, String> result) throws Exception {
		if (prop.getReadMethod().getAnnotation(CouldNotBeEmpty.class) == null)
			return;
		
		Class type = prop.getPropertyType();
		Object valueObj = prop.getReadMethod().invoke(target);
		if (String.class.equals(type)) {
			String value = (String) valueObj;
			if ((value == null) || (value.trim().length() == 0))
				reportEmptyError(target, result, prop);

		} else if (BusinessEntity.class.isAssignableFrom(type)) {
			BusinessEntity value = (BusinessEntity) valueObj;
			if ((value == null) || (value.getId() == null)
					|| (value.getId() <= 0))
				reportEmptyError(target, result, prop);

		} else if (Date.class.isAssignableFrom(type)) {
			Date value = (Date) valueObj;
			if ((value == null) || (value.getTime() == 0))
				reportEmptyError(target, result, prop);

		} else if (Object.class.isAssignableFrom(type)) {
			if (valueObj == null)
				reportEmptyError(target, result, prop);
		}
	}

	public static void validateGreaterThen(Object target, PropertyDescriptor field, Map<String, String> errors, Date dateLimit) throws Exception{
		if (field.getReadMethod().getAnnotation(GreaterThen.class) != null) {
			if (field.getReadMethod().invoke(target) == null)
				return;
			if (Date.class.isAssignableFrom(field.getReadMethod().getReturnType())){
				if (dateLimit.getTime() > ((Date) field.getReadMethod().invoke(target)).getTime() ){
					errors.put(target.getClass().getSimpleName() + "."
							+ field.getName(), 
							field.getReadMethod().getAnnotation(GreaterThen.class).message());
				}
			}
			Class type = field.getReadMethod().getReturnType();
			if (Long.class.isAssignableFrom(type)
					|| Integer.class.isAssignableFrom(type)
					|| Double.class.isAssignableFrom(type)
					|| Float.class.isAssignableFrom(type)){
				Object v = field.getReadMethod().invoke(target);
				if (v== null)
					return;
				Double val = Double.valueOf(v.toString());
				
				Double limit = field.getReadMethod().getAnnotation(GreaterThen.class).lowerLimit();
				if (field.getReadMethod().getAnnotation(GreaterThen.class).including()){
					if (limit > val)
						errors.put(target.getClass().getSimpleName() + "."
								+ field.getName(), 
								field.getReadMethod().getAnnotation(GreaterThen.class).message());
				} else {
					if (limit >= val)	
						errors.put(target.getClass().getSimpleName() + "."
								+ field.getName(), 
								field.getReadMethod().getAnnotation(GreaterThen.class).message());					
				}
					
			}
		}
	}
	
	public static void reportNumberFormatException(Object target,
			PropertyDescriptor field, Map<String, String> errors)
			throws Exception {
		Class type = field.getReadMethod().getReturnType();
		if (Double.class.isAssignableFrom(type)
				|| Float.class.isAssignableFrom(type)) {
			errors
					.put(target.getClass().getSimpleName() + "."
							+ field.getName(),
							"Значение поля должно быть действительным числом. Пример: 3.1");
		}
		if (Long.class.isAssignableFrom(type)
				|| Integer.class.isAssignableFrom(type)) {
			errors
			.put(target.getClass().getSimpleName() + "."
					+ field.getName(),
					"Значение поля должно быть целім числом. Пример: 6");
		}
	}

	public static void validateStringLen(Object target,
			PropertyDescriptor prop, Map<String, String> result)
			throws Exception {
		if ("textView".equalsIgnoreCase(prop.getName()))
			return;
		if (prop.getReadMethod().getReturnType().equals(String.class)) {
			int maxLen = 50;
			if (prop.getReadMethod().getAnnotation(MaxLength.class) != null)
				maxLen = prop.getReadMethod().getAnnotation(MaxLength.class)
						.value();
			String value = (String) prop.getReadMethod().invoke(target);
			if (value != null && value.length() > maxLen) {
				reportError("Максимальная длина содержимого текстового поля не может превішать "
						+ maxLen + " символов.", target, result, prop);
			}
		}
	}

	private static void reportError(String msg, Object target,
			Map<String, String> errors, PropertyDescriptor field) {
		errors.put(target.getClass().getSimpleName() + "." + field.getName(),
				msg);
	}

	private static void reportEmptyError(Object target,
			Map<String, String> errors, PropertyDescriptor field) {
		errors.put(target.getClass().getSimpleName() + "." + field.getName()

		, field.getReadMethod().getAnnotation(CouldNotBeEmpty.class).message());
	}
}
