package com.kt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "test")
public record TestProperties(
	String myValue,
	String myValueParam
) {
}
