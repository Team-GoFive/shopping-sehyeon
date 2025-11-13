package com.kt.dto.user;

import java.time.LocalDateTime;

import com.kt.domain.user.User;

public class UserResponse {
	public record Search(
		Long id,
		String name,
		LocalDateTime createdAt
	) {

	}

	public record Detail(
		Long id,
		String name,
		String email
	) {
		public static Detail of(User user) {
			return new Detail(
				user.getId(),
				user.getName(),
				user.getEmail()
			);
		}
	}
}
