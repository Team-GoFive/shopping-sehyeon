package com.kt.common.interceptor;

import java.security.Principal;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kt.common.support.VisitorEvent;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VisitStatInterceptor implements HandlerInterceptor {
	private final ApplicationEventPublisher applicationEventPublisher;

	// 컨트롤러 이전에 위치
	// userId, action(주문생성), type(사용자, 관리자)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Optional<Principal> principal = Optional.ofNullable(request.getUserPrincipal());
		Long name = principal.isPresent() ? Long.parseLong(principal.get().getName()) : null;
		applicationEventPublisher.publishEvent(
			new VisitorEvent(
				request.getRemoteAddr(),
				request.getHeader("User-Agent"),
				name
			)
		);
		return true;
	}
}
