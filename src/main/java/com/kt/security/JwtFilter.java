package com.kt.security;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kt.service.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final String TOKEN_PREFIX = "Bearer ";
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		// header가 비어있으면 다음 필터로 넘기기
		if (Strings.isBlank(header)) {
			filterChain.doFilter(request, response);
			return;
		}

		// Bearer 접두사 제거
		String token = header.substring(TOKEN_PREFIX.length());

		// token의 유효성 검사 진행(서명)
		// 유효하지 않으면 다음 필터로 넘기기
		if (jwtService.validate(token) == false) {
			filterChain.doFilter(request, response);
			return;
		}

		Long id = jwtService.parseId(token);
		// token에서 id 파싱
		TechUpAuthenticationToken techUpToken = new TechUpAuthenticationToken(
			new DefaultCurrentUser(id, "파싱한 아이디"),
			List.of()
		);

		SecurityContextHolder.getContext().setAuthentication(techUpToken);

		filterChain.doFilter(request, response);
	}
}
