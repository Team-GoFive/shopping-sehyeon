package com.kt.aspect.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kt.domain.history.HistoryType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ShoppingLogger {
	HistoryType type();

	String content() default "";

}
