package com.kt.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.response.ApiResult;
import com.kt.common.support.SwaggerAssistance;
import com.kt.dto.user.UserRequest;
import com.kt.security.CurrentUser;
import com.kt.service.user.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController extends SwaggerAssistance {
	private final UserService userService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	// json 형태의 body에 담겨서 post 요청으로 /users로 들어오면
	// @RequestBody를 보고 jacksonObjectMapper가 동작해서 json을 읽어서 dto로 변환
	public ApiResult<?> create(
		@RequestBody UserRequest.Create request
	) {
		userService.create(
			request.loginId(),
			request.password(),
			request.name(),
			request.mobile(),
			request.email(),
			request.gender(),
			request.birthday()
		);
		return ApiResult.ok();
	}

	// boolean, Boolean 차이
	// boolean: primitive type, null 불가능, 기본값 false
	// Boolean: reference type, null 가능
	// null 가능성을 고려해야 할 때 Boolean 사용
	@GetMapping("/duplicate-login-id")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> isDuplicateLoginId(
		@RequestParam String loginId
	) {
		var duplicateLoginId = userService.isDuplicateLoginId(loginId);
		return ApiResult.ok(duplicateLoginId);
	}

	//uri는 식별이 가능해야한다.
	// 어떤 유저?, put, patch, post는 body에 담아서 보내야함
	@PutMapping("/{id}/update-password")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> updatePassword(
		@PathVariable Long id,
		@RequestBody UserRequest.PasswordUpdate request
	) {
		userService.changePassword(
			id,
			request.oldPassword(),
			request.newPassword()
		);
		return ApiResult.ok();
	}

	@GetMapping("/orders")
	@ResponseStatus(HttpStatus.OK)
	public void getOrders(
		@AuthenticationPrincipal CurrentUser currentUser
	) {
		currentUser.getId();
	}
}
