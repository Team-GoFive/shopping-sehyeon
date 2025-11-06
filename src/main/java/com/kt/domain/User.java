package com.kt.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private LocalDate birthday;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public User(String loginId, String password, String name, String mobile, String email, Gender gender, LocalDate birthday) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.gender = gender;
		this.birthday = birthday;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
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
