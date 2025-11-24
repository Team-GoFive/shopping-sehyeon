package com.kt.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.domain.order.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String loginId;
	private String password;
	private String name;
	private String mobile;
	private String email;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	private Role role;
	private LocalDate birthday;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user")
	private List<Order> orders = new ArrayList<>();

	public User(
		String loginId,
		String password,
		String name,
		String mobile,
		String email,
		Gender gender,
		Role role,
		LocalDate birthday
	) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.gender = gender;
		this.role = role;
		this.birthday = birthday;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public static User normalUser(
		String loginId,
		String password,
		String name,
		String email,
		String mobile,
		Gender gender,
		LocalDate birthday
	) {
		return new User(
			loginId,
			password,
			name,
			mobile,
			email,
			gender,
			Role.USER,
			birthday
		);
	}

	public static User admin(
		String loginId,
		String password,
		String name,
		String email,
		String mobile,
		Gender gender,
		LocalDate birthday
	) {
		return new User(
			loginId,
			password,
			name,
			mobile,
			email,
			gender,
			Role.ADMIN,
			birthday
		);
	}

	// set 보다는 change로 더 명확하게 표현
	public void changeMobile(String mobile) {
		this.mobile = mobile;
	}

	public void changeEmail(String email) {
		this.email = email;
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public void changeName(String name) {
		this.name = name;
	}

}
