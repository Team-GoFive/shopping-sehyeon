package com.kt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "common")
public record CommonProperties(
	String commonValue,
	String commonValueParam
) {
}
