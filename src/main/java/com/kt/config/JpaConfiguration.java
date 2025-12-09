package com.kt.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kt.security.DefaultCurrentUser;

@EnableJpaAuditing
// @Configuration
public class JpaConfiguration {

	@Bean
	public AuditorAware<? extends Long> auditorProvider() {
		return () -> {
			SecurityContext context = SecurityContextHolder.getContext();
			Object principal = context.getAuthentication().getPrincipal();

			return principal instanceof DefaultCurrentUser currentUser ?
				Optional.of(currentUser.getId()) :
				Optional.empty();
		};
	}
}
