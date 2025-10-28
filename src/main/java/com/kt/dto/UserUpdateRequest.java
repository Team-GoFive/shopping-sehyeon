package com.kt.dto;

import java.time.LocalDate;

// 수정 가능 옵션: 비밀번호, 이름, 생일
public record UserUpdateRequest(
	String password,
	String name,
	LocalDate birthday
) {
}
