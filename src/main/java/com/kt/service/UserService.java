package com.kt.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.Gender;
import com.kt.domain.user.User;
import com.kt.repository.UserRepository;
import com.kt.validator.UserPasswordValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	public static final String RESET_PASSWORD = "Abcd1234!";
	private final UserRepository userRepository;

	public void create(
		String loginId,
		String password,
		String name,
		String mobile,
		String email,
		Gender gender,
		LocalDate birthday
	) {
		// repository로 넘길거임
		User user = User.normalUser(
			loginId,
			password,
			name,
			mobile,
			email,
			gender,
			birthday
		);
		userRepository.save(user);
	}

	public Boolean isDuplicateLoginId(String loginId) {
		return userRepository.existsByLoginId(loginId);
	}

	public void changePassword(Long id, String oldPassword, String password) {
		var user = userRepository.findByIdOrThrow(id);

		UserPasswordValidator.validate(oldPassword, password, user);

		user.changePassword(password);
	}

	public Page<User> searchUsers(String keyword, Pageable pageable) {
		return userRepository.findAllByNameContaining(keyword, pageable);
	}

	public User detail(Long id) {
		// id에 해당하는 user 조회
		return userRepository.findByIdOrThrow(id);
	}

	public void update(Long id, String name, String mobile, String email) {
		// loginId, 변경된 user 정보 넘김
		var user = userRepository.findByIdOrThrow(id);

		user.changeName(name);
		user.changeMobile(mobile);
		user.changeEmail(email);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	public void resetPassword(Long id) {
		var user = userRepository.findByIdOrThrow(id);

		user.changePassword(RESET_PASSWORD);
	}
}
