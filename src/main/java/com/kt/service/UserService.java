package com.kt.service;

import org.springframework.stereotype.Service;

import com.kt.domain.User;
import com.kt.dto.UserCreateRequest;
import com.kt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;

	public void create(UserCreateRequest request){
		System.out.println("UserService, request = " + request);

		// repository로 넘길거임
		User user = new User(request.loginId(), request.password(), request.name(), request.birthday());
		repository.save(user);
	}
}
