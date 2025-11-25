package com.kt.controller.auth;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.aspect.logger.ShoppingLogger;
import com.kt.common.response.ApiResult;
import com.kt.domain.history.HistoryType;
import com.kt.dto.auth.LoginRequest;
import com.kt.dto.auth.LoginResponse;
import com.kt.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@ShoppingLogger(type = HistoryType.LOGIN, content = "사용자 로그인")
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		Pair<String, String> pair = authService.login(
			request.loginId(),
			request.password()
		);
		return ApiResult.ok(new LoginResponse(pair.getFirst(), pair.getSecond()));
	}
}
