package com.kt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class ActiveProfileConfig {

	private final Environment environment;

	@Bean
	public String init() {
		System.out.println("Active Profile : " + environment.getActiveProfiles()[0]);
		return "";
	}
}
