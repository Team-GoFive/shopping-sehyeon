package com.kt.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kt.domain.User;
import com.kt.dto.CustomPage;
import com.kt.dto.UserCreateRequest;
import com.kt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;

	public void create(UserCreateRequest request) {
		// repository로 넘길거임
		Long id = repository.findMaxId();
		User user = new User(
			id == null ? 1L : id + 1,
			request.loginId(),
			request.password(),
			request.name(),
			request.mobile(),
			request.email(),
			request.gender(),
			request.birthday(),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
		repository.save(user);
	}

	public Boolean isDuplicateLoginId(String loginId) {
		return repository.existsByLoginId(loginId);
	}

	public void changePassword(Long id, String oldPassword, String password) {

		// 유저를 조회해서 비밀번호가 같은지 조회
		var user = repository.selectById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

		// 기존 비밀번호와 동일한 비밀번호로 변경할 수 없음
		if (oldPassword.equals(password)) {
			throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
		}

		if (!user.getPassword().equals(oldPassword)) {
			throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
		}

		repository.updatePassword(id, password);
	}

	public CustomPage searchUsers(int page, int size, String keyword) {
		// repository에서 user 목록 조회
		// first: user 목록, second: 전체 user 수
		var pair = repository.selectAll(page - 1, size, keyword);
		var pages = (int)Math.ceil((double)pair.getSecond() / size);

		return new CustomPage(
			pair.getFirst(),
			page,
			size,
			pages,
			pair.getSecond()
		);
	}

	public User detail(Long id) {
		// id에 해당하는 user 조회
		return repository.selectById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
	}

	public void update(Long id, String name, String mobile, String email) {
		// loginId, 변경된 user 정보 넘김
		repository.updateById(id, name, mobile, email);
	}

	public void delete(String loginId) {
		// loginId에 해당하는 user 삭제
		repository.delete(loginId);
	}
}
