package com.kt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ActiveProfileConfig {

	private final Environment environment;

	private final TestProperties testProperties;

	private final CommonProperties commonProperties;

	@Bean
	public String init() {
		System.out.println(commonProperties.commonValue());
		System.out.println(commonProperties.commonValueParam());
		System.out.println(testProperties.myValue());
		System.out.println(testProperties.myValueParam());
		// System.out.println("Active Profile : " + environment.getActiveProfiles()[0]);
		return "";
	}
}
