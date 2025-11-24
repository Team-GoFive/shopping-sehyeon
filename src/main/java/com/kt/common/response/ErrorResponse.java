package com.kt.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(
	String code,
	String message
) {

	public static ErrorResponse of(HttpStatus status, String message) {
		return new ErrorResponse(status.series().name(), message);
	}

	public static ResponseEntity<ErrorResponse> error(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(ErrorResponse.of(status, message));
	}
}
