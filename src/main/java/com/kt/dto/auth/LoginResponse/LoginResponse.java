package com.kt.dto.auth.LoginResponse;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
