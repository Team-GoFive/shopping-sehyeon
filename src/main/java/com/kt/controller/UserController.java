package com.kt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dto.UserCreateRequest;
import com.kt.dto.UserUpdateRequest;
import com.kt.service.UserService;

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
	public String create(@RequestBody UserCreateRequest request){
		// jackson object mapper -> json to dto 매핑
		userService.create(request);
		return "hihi";
	}

	@PutMapping()
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestParam String loginId, @RequestBody UserUpdateRequest request){
		userService.update(loginId, request);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void delete(@RequestParam String loginId){
		userService.delete(loginId);
	}
}
