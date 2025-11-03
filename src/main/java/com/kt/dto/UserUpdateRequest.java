package com.kt.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

// 수정 가능 옵션: 비밀번호, 이름, 생일
public record UserUpdateRequest(
	@NotBlank
	String name,
	@NotBlank
	String mobile,
	@NotBlank
	String email
) {
}
