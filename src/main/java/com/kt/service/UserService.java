package com.kt.service;

import org.springframework.stereotype.Service;

import com.kt.domain.User;
import com.kt.dto.UserCreateRequest;
import com.kt.dto.UserUpdateRequest;
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

	public void update(String loginId, UserUpdateRequest request) {
		// loginId에 해당하는 user 조회
		User user = repository.select(loginId);
		System.out.println("user = " + user);
		// request에 따라 user 정보 변경
		user.changePassword(request.password());
		user.changeName(request.name());
		user.changeBirthday(request.birthday());
		// loginId, 변경된 user 정보 넘김
		repository.update(loginId, user);
		System.out.println("user 변경 완료");
	}
}
