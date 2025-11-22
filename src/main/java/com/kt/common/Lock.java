package com.kt.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Lock {
	Key key();

	long waitTime() default 600L;

	long leaseTime() default 500L;

	TimeUnit timeUnit() default TimeUnit.MICROSECONDS;

	enum Key {
		PRODUCT,
		STOCK,
		USER_EVENT
	}
}
