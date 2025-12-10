package com.kt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.kt.common.profile.ProdProfile;

@ProdProfile
@ConfigurationProperties(prefix = "test")
public record TestProperties(
	String myValue,
	String myValueParam
) {
}
