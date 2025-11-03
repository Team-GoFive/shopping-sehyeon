package com.kt.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kt.domain.Gender;
import com.kt.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;
	// user 데이터 조회를 위한 mapper 클래스

	private User mapToUser(ResultSet rs) throws SQLException {
		String genderStr = rs.getString("gender");
		Gender gender = null;
		if (genderStr != null && !genderStr.isBlank()) {
			try {
				gender = Gender.valueOf(genderStr.toUpperCase());
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
	}

	private final RowMapper<User> rowMapper() {
		return (rs, rowNum) -> mapToUser(rs);
	}

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

	// id값으로 db에 조회해서 User 객체를 반환하는 메서드 필요
	public Optional<User> selectById(Long id) {
		String sql = "SELECT * FROM member WHERE id = ?";
		var list = jdbcTemplate.query(sql, rowMapper(), id);
		return list.stream().findFirst();
	}

	public boolean existsById(Long id) {
		String sql = "SELECT EXISTS (SELECT 1 FROM MEMBER WHERE id = ?)";
		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
	}

	// updatePassword
	public void updatePassword(Long id, String newPassword) {
		// UPDATE {table} SET {column} = {value} WHERE {condition}
		String sql = "UPDATE member SET password = ?, updatedAt = ? WHERE id = ?";
		jdbcTemplate.update(sql, newPassword, id);
	}

	// 전체 user 조회
	public Pair<List<User>, Long> selectAll(int page, int size, String keyword) {
		// paging의 구조
		// 백엔드 입장에서 필요한 것
		// 1. 한 화면에 몇개 보여줄 것인가? -> pageSize
		// 2. 내가 몇번째 페이지를 보고 있나? -> pageNumber
		// offset = (pageNumber - 1) * pageSize
		// limit = pageSize

		// 프론트엔드에서 페이징을 구현할때 필요한 정보
		// 데이터들
		// 1. 한 화면에 몇개 보여줄 것인가? -> limit (pageSize)
		// 2. 내가 몇번째 페이지를 보고 있나? -> 전달받은거 그대로 보내주기
		// 3. 몇개의 페이지가 생기나?(백엔드가 좋아하는 영역)
		// 4. 전체 데이터가 몇개인가? -> totalElements (무조건)

		// offset 방식은 full-scan이 발생할 수 있어서 데이터가 많아지면 느려질 수 있음
		// cursor 기반 페이징이 더 효율적이지만 구현이 더 복잡함
		// 키워드 검색 = LIKE 연산자 사용 %keyword%(포함), %keyword(앞에 무조건), keyword%(뒤에 무조건)
		String sql = "SELECT * FROM member WHERE name LIKE CONCAT('%', ?, '%') LIMIT ? OFFSET ?";
		var users = jdbcTemplate.query(sql, rowMapper(), keyword, size, page);
		String countSql = "SELECT COUNT(*) FROM member";
		var totalElements = jdbcTemplate.queryForObject(countSql, Long.class);

		return Pair.of(users, totalElements);
	}

	public void search(int page, int size) {

	}

	public User select(String loginId) {
		String sql = "SELECT * FROM member WHERE loginId = ?";
		return jdbcTemplate.queryForObject(sql, rowMapper(), loginId);
	}

	public void updateById(Long id, String name, String email, String mobile) {
		String sql = "UPDATE member SET name = ?, email = ?, mobile = ?, updatedAt = ? WHERE id = ?";
		jdbcTemplate.update(sql, name, email, mobile, LocalDateTime.now().toString(), id);
	}

	public void deleteById(Long id) {
		String sql = "DELETE FROM member WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
