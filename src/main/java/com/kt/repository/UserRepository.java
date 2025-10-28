package com.kt.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kt.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public void save(User user){
		System.out.println("UserRepository, save " + user.toString());

		String sql = "INSERT INTO MEMBER (loginId, password, name, birthday) VALUES (?,?,?,?)";
		// 서비스에서 dto를 도메인(비즈니스모델)으로 바꾼다음 전
		jdbcTemplate.update(sql, user.getLoginId(), user.getPassword(), user.getName(), user.getBirthday().toString());

	}
}
