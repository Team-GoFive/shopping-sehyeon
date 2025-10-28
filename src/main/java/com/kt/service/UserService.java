package com.kt.service;

import java.util.List;

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

	public List<User> getUsers() {
		return repository.selectAll();
	}

	public User getUser(String loginId) {
		return repository.select(loginId);
	}

	public void update(String loginId, UserUpdateRequest request) {
		// loginId, 변경된 user 정보 넘김
		repository.update(loginId, request);
	}

	public void delete(String loginId) {
		// loginId에 해당하는 user 삭제
		repository.delete(loginId);
	}
}
