package org.bakery.server.validation;

public @interface MaxLength {
	int value() default 50;
}
