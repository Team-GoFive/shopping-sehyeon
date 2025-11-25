package com.kt.aspect.logger;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.kt.security.CurrentUser;
import com.kt.service.history.HistoryService;

import lombok.RequiredArgsConstructor;

// @Aspect
// @Component
// @RequiredArgsConstructor
// public class LoggerAspect {
// 	private final HistoryService historyService;
//
// 	@Around("@annotation(com.kt.common.support.TechUpLogger) && annotation(logger)")
// 	public Object log(ProceedingJoinPoint joinPoint, TechUpLogger techUpLogger) throws Throwable {
//
// 		HistoryType type = techUpLogger.type();
// 		String content = techUpLogger.content();
//
// 		historyService.create(type, content, null);
// 		return joinPoint.proceed();
// 	}
// }

@Component
@Aspect
@RequiredArgsConstructor
public class LoggerAspect {

	private final HistoryService historyService;

	@Around("@annotation(ShoppingLogger) && @annotation(shoppingLogger)")
	public Object shoppingLogger(ProceedingJoinPoint joinPoint, ShoppingLogger shoppingLogger) throws Throwable {

		CurrentUser principal = (CurrentUser)Arrays.stream(joinPoint.getArgs())
			.filter(arg -> arg instanceof CurrentUser)
			.findFirst().orElse(null);

		Long userId = principal != null ? principal.getId() : null;

		historyService.create(
			shoppingLogger.type(),
			shoppingLogger.content(),
			userId
		);

		return joinPoint.proceed();
	}

}
