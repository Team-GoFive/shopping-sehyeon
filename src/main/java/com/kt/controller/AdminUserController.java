package com.kt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.domain.User;
import com.kt.dto.CustomPage;
import com.kt.dto.UserUpdateRequest;
import com.kt.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

	// 유저 리스트 조회
	// 유저 상세 조회
	// 유저 정보 수정
	// 유저 삭제
	// 유저 비밀번호 초기화

	private final UserService userService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CustomPage search(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String keyword
	) {
		return userService.searchUsers(page, size, keyword);
	}

	@GetMapping("/detail")
	@ResponseStatus(HttpStatus.OK)
	public User detail(@RequestParam Long id) {
		return userService.detail(id);
	}

	// todo: 유저 정보 수정
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
		userService.update(id, request.name(), request.mobile(), request.email());
	}

	// todo: 유저 삭제
	// DELETE FROM MEMBER WHERE id = ?
	// todo: 유저 비밀번호 초기화
}
