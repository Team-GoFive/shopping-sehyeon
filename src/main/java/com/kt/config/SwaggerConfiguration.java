package com.kt.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

// @Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "테크업 쇼핑몰",
		version = "v1.0",
		description = "테크업 쇼핑몰 API 명세서"
	)
)
@RequiredArgsConstructor
public class SwaggerConfiguration {
	private final Environment environment;

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.servers(
				List.of(
					new Server().url(getServerUrl())
				)
			)
			.components(
				new Components()
					.addSecuritySchemes(
						"Bearer Authentication",
						new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")
					)
			);
	}

	private String getServerUrl() {
		// local, default -> localhost:8080
		// dev -> dev.ktechup.com
		// prod -> ktechup.com
		String profile = environment.getActiveProfiles()[0];
		switch (profile) {
			case "local" -> profile = "localhost:8080";
			case "test" -> profile = "localhost:8080";
			case "dev" -> profile = "dev.ktechup.com";
			case "prod" -> profile = "ktechup.com";
			case null -> profile = "localhost:8080";
			default -> profile = "localhost:8080";
		}
		return profile;
	}

	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder()
			.group("User API")
			.pathsToExclude("/api/admin/**")
			.build();
	}

	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi.builder()
			.group("Admin API")
			.pathsToMatch("/api/admin/**", "/api/auth/**")
			.build();
	}
}
