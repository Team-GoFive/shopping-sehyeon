package com.kt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.User;
import com.kt.dto.UserCreateRequest;
import com.kt.repository.UserJdbcRepository;
import com.kt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	public static final String RESET_PASSWORD = "Abcd1234!";
	private final UserRepository userRepository;

	public void create(UserCreateRequest request) {
		// repository로 넘길거임
		User user = new User(
			request.loginId(),
			request.password(),
			request.name(),
			request.mobile(),
			request.email(),
			request.gender(),
			request.birthday()
		);
		userRepository.save(user);
	}

	public Boolean isDuplicateLoginId(String loginId) {
		return userRepository.existsByLoginId(loginId);
	}

	public void changePassword(Long id, String oldPassword, String password) {
		var user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

		// 기존 비밀번호와 동일한 비밀번호로 변경할 수 없음
		if (oldPassword.equals(password)) {
			throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
		}

		if (!user.getPassword().equals(oldPassword)) {
			throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
		}

		user.changePassword(password);
	}

	public Page<User> searchUsers(String keyword, Pageable pageable) {
		return userRepository.findAllByNameContaining(keyword, pageable);
	}

	public User detail(Long id) {
		// id에 해당하는 user 조회
		return userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
	}

	public void update(Long id, String name, String mobile, String email) {
		// loginId, 변경된 user 정보 넘김
		var user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

		user.changeName(name);
		user.changeMobile(mobile);
		user.changeEmail(email);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	public void resetPassword(Long id){
		var user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다"));

		user.changePassword(RESET_PASSWORD);
	}
}
