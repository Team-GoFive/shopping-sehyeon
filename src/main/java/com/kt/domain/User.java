package com.kt.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	private Long id;
	private String loginId;
	private String password;
	private String name;
	private String mobile;
	private String email;
	private Gender gender;
	private LocalDate birthday;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// set 보다는 change로 더 명확하게 표현
	public void changePassword(String password) {
		this.password = password;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void changeBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
}
