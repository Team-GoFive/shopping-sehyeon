package com.kt.common.exception;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kt.common.response.ErrorResponse;
import com.kt.common.support.Message;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Hidden
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiAdvice {
	private final ApplicationEventPublisher publisher;

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> customException(CustomException e) {
		return ErrorResponse.error(e.getErrorCode().getStatus(), e.getErrorCode().getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> internalServerError(Exception e) {
		publisher.publishEvent(new Message(e.getMessage()));

		log.error(Exceptions.simplify(e));
		return ErrorResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 백엔드 팀에 문의하세요");
	}
}
