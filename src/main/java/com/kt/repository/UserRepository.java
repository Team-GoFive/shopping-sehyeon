package com.kt.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kt.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public void save(User user){
		String sql = "INSERT INTO MEMBER (loginId, password, name, birthday) VALUES (?,?,?,?)";
		// 서비스에서 dto를 도메인(비즈니스모델)으로 바꾼다음 전
		jdbcTemplate.update(sql, user.getLoginId(), user.getPassword(), user.getName(), user.getBirthday().toString());
	}

	public User select(String loginId){
		String sql = "SELECT * FROM member WHERE loginId = ?";
		return jdbcTemplate.queryForObject(sql, userMapper, loginId);
	}

	public List<User> selectAll() {
		String sql = "SELECT * FROM member";
		return jdbcTemplate.query(sql, userMapper);
	}

	public void update(String loginId, User user){
		String sql = "UPDATE member SET password = ?, name = ?, birthday = ? WHERE loginId = ?";
		jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getBirthday(), loginId);
	}

	public void delete(String loginId) {
		String sql = "DELETE FROM member WHERE loginId = ?";
		jdbcTemplate.update(sql, loginId);
	}

	// user 데이터 조회를 위한 mapper 클래스
	RowMapper<User> userMapper = (rs, rowNum) -> {
		User user = new User(
			rs.getString("loginId"),
		rs.getString("password"),
		rs.getString("name"),
		rs.getDate("birthday").toLocalDate()
		);
		return user;
	};
}
