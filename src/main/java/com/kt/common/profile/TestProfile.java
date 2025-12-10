package com.kt.common.profile;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Profile;

@Profile("test")
@Retention(RetentionPolicy.RUNTIME)
public @interface TestProfile {
}
