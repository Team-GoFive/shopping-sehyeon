package com.kt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class AopTransactionManagerImpl implements AopTransactionManager {
	@Override
	public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
		return joinPoint.proceed();
	}
}
