package com.kt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.domain.User;
import com.kt.dto.UserCreateRequest;
import com.kt.dto.UserUpdatePasswordRequest;
import com.kt.dto.UserUpdateRequest;
import com.kt.service.UserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	// json 형태의 body에 담겨서 post 요청으로 /users로 들어오면
	// @RequestBody를 보고 jacksonObjectMapper가 동작해서 json을 읽어서 dto로 변환
	public void create(@RequestBody UserCreateRequest request) {
		// jackson object mapper -> json to dto 매핑
		userService.create(request);
	}

	// boolean, Boolean 차이
	// boolean: primitive type, null 불가능, 기본값 false
	// Boolean: reference type, null 가능
	// null 가능성을 고려해야 할 때 Boolean 사용
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "중복 여부 반환"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청"),
			@ApiResponse(responseCode = "500", description = "서버 오류")
		}
	)
	@GetMapping("/duplicate-login-id")
	@ResponseStatus(HttpStatus.OK)
	public Boolean isDuplicateLoginId(@RequestParam String loginId) {
		return userService.isDuplicateLoginId(loginId);
	}

	//uri는 식별이 가능해야한다.
	// 어떤 유저?, put, patch, post는 body에 담아서 보내야함
	@PutMapping("/{id}/update-password")
	@ResponseStatus(HttpStatus.OK)
	public void updatePassword(
		@PathVariable Long id,
		@RequestBody UserUpdatePasswordRequest request
	) {
		userService.changePassword(id, request.oldPassword(), request.newPassword());
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<User> getUsers() {
		return userService.getUsers();
	}

	// user 조회(loginId)
	@GetMapping("/{loginId}")
	@ResponseStatus(HttpStatus.OK)
	public User getUser(@PathVariable String loginId) {
		return userService.getUser(loginId);
	}

	@PutMapping("/{loginId}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable String loginId, @RequestBody UserUpdateRequest request) {
		userService.update(loginId, request);
	}

	@DeleteMapping("/{loginId}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String loginId) {
		userService.delete(loginId);
	}
}
