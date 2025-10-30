package com.kt.dto;

import java.time.LocalDate;

import com.kt.domain.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(
	@NotBlank
	String loginId,
	@NotBlank
	// 최소 8자, 대문자, 소문자, 숫자, 특수문자 포함
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	String password,
	@NotBlank
	String name,
	@NotBlank
	// 이메일 형식
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
	String email,
	@NotBlank
	// 000-0000-0000
	@Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
	String mobile,
	@NotNull
	Gender gender,
	@NotNull
	LocalDate birthday
) {
}
