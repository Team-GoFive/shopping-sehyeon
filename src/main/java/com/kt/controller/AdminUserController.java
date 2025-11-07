package com.kt.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.common.Paging;
import com.kt.dto.user.UserRequest;
import com.kt.dto.user.UserResponse;
import com.kt.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User")
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController extends SwaggerAssistance {

	private final UserService userService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> search(
		@RequestParam(required = false) String keyword,
		@ParameterObject Paging paging
	) {
		var users = userService.searchUsers(keyword, paging.toPageable());
		var data = users.map(user ->
			new UserResponse.Search(
				user.getId(),
				user.getName(),
				user.getCreatedAt()
			)
		);
		return ApiResult.ok(data);
	}

	@GetMapping("/detail")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> detail(
		@RequestParam Long id
	) {
		var user = UserResponse.Detail.of(userService.detail(id));
		return ApiResult.ok(
			new UserResponse.Detail(
				user.id(),
				user.email(),
				user.name()
			)
		);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> update(
		@PathVariable Long id,
		@RequestBody UserRequest.Update request
	) {
		userService.update(
			id,
			request.name(),
			request.mobile(),
			request.email()
		);
		return ApiResult.ok();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> delete(
		@PathVariable Long id
	) {
		userService.delete(id);
		return ApiResult.ok();
	}

	@PutMapping("/{id}/reset-password")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> resetPassword(@PathVariable Long id) {
		userService.resetPassword(id);
		return ApiResult.ok();
	}
}
