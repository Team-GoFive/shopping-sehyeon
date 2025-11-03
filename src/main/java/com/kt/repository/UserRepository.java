package com.kt.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kt.domain.Gender;
import com.kt.domain.User;
import com.kt.dto.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;
	// user 데이터 조회를 위한 mapper 클래스
	private final RowMapper<User> userMapper = (rs, rowNum) -> {
		String genderStr = rs.getString("gender");
		Gender gender = null;
		if (genderStr != null && !genderStr.isBlank()) {
			try {
				gender = com.kt.domain.Gender.valueOf(genderStr.toUpperCase());
			} catch (IllegalArgumentException ignored) {
				gender = null;
			}
		}

		User user = new User(
			rs.getLong("id"),
			rs.getString("loginId"),
			rs.getString("password"),
			rs.getString("name"),
			rs.getString("mobile"),
			rs.getString("email"),
			gender,
			rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null,
			rs.getTimestamp("createdAt") != null ? rs.getTimestamp("createdAt").toLocalDateTime() : null,
			rs.getTimestamp("updatedAt") != null ? rs.getTimestamp("updatedAt").toLocalDateTime() : null
		);
		return user;
	};

	public void save(User user) {
		String sql = "INSERT INTO member (id, loginId, password, name, email, mobile, gender, birthday, createdAt, updatedAt) VALUES (?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(
			sql,
			user.getId(),
			user.getLoginId(),
			user.getPassword(),
			user.getName(),
			user.getEmail(),
			user.getMobile(),
			user.getGender() != null ? user.getGender().toString() : null,
			user.getBirthday().toString(),
			user.getCreatedAt().toString(),
			user.getUpdatedAt().toString()
		);
	}

	// id max값 찾기
	public Long findMaxId() {
		String sql = "SELECT MAX(id) FROM member";
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	public User select(String loginId) {
		String sql = "SELECT * FROM member WHERE loginId = ?";
		return jdbcTemplate.queryForObject(sql, userMapper, loginId);
	}

	public List<User> selectAll() {
		String sql = "SELECT * FROM member";
		return jdbcTemplate.query(sql, userMapper);
	}

	public void update(String loginId, UserUpdateRequest user) {
		String sql = "UPDATE member SET password = ?, name = ?, birthday = ? WHERE loginId = ?";
		jdbcTemplate.update(sql, user.password(), user.name(), user.birthday(), loginId);
	}

	public void delete(String loginId) {
		String sql = "DELETE FROM member WHERE loginId = ?";
		jdbcTemplate.update(sql, loginId);
	}

	// 크게 세가지 정도 아이디 중복 체크 방법
	// 1. count해서 0보다 큰지 체크
	// -> db에 만약 유저가 3000만명 있다면 3000만건을 다 뒤져야함(full-scan). 비효율적
	// 2. unique 제약조건 걸고 예외처리
	// -> 제약조건 위반 예외가 발생. 데이터베이스에서 발생하는 에러 처리는 복잡하고 번거로움. DataViolation Exception 처리가 꽤나 번거로움
	// 3. exist로 존재 여부 체크 ✅
	// -> boolean으로 값 존재 여부를 바로 알 수 있음
	public boolean existsByLoginId(String loginId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM MEMBER WHERE loginId = ?)";
		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, loginId));
	}

	// updatePassword
	public void updatePassword(int id, String newPassword) {
		// UPDATE {table} SET {column} = {value} WHERE {condition}
		var sql = "UPDATE member SET password = ? WHERE id = ?";
		jdbcTemplate.update(sql, newPassword, id);
	}
}
