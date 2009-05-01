package org.bakery.server.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Validation annotation that places restriction on annotated field.
 * Usage rules for field types:
 * 	- Date: fields does not matter
 * 			specifies that date could not be date in past. 
 *  - Long: natural greater than
 *  - Double: natural greater than  
 * 		 
 * 		null value passes this validation
 * @author Odmin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GreaterThen {
	double lowerLimit() default 0;
	boolean including() default false;
	String message();
}
