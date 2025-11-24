package com.kt.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Profile;

@Profile({"prod", "dev"})
@Retention(RetentionPolicy.RUNTIME)
public @interface AppProfile {
}
