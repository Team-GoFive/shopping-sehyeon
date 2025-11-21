package com.kt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kt.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // Spring Security 활성화
@EnableMethodSecurity // 메서드 단위 권한 설정 활성화
@RequiredArgsConstructor
public class SecurityConfiguration {

	private static final String[] GET_PERMIT_ALL = {
		"/api/users/**",
	};
	private static final String[] POST_PERMIT_ALL = {
		"/api/users/**",
		"/api/auth/**",
		"/api/products/**"
	};
	private static final String[] PUT_PERMIT_ALL = {};
	private static final String[] PATCH_PERMIT_ALL = {};
	private static final String[] DELETE_PERMIT_ALL = {};
	private final JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authorizeHttpRequests(
				request -> {
					request.requestMatchers(HttpMethod.GET, GET_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.POST, POST_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.PUT, PUT_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.PATCH, PATCH_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.DELETE, DELETE_PERMIT_ALL).permitAll();
				}
			)
			.authorizeHttpRequests(request -> request.anyRequest().authenticated())
			.csrf(csrf -> csrf.disable())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
