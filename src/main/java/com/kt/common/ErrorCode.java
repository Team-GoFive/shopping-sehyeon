package com.kt.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다"),
	NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다"),
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다"),
	SAME_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다."),
	INVALID_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않습니다."),

	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "유효하지 않은 요청 파라미터입니다"),
	FAIL_ACQUIRED_LOCK(HttpStatus.BAD_REQUEST, "락 획득에 실패했습니다."),
	ERROR_SYSTEM(HttpStatus.INTERNAL_SERVER_ERROR, "시스템 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String message;
}
