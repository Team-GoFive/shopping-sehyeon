package com.kt.validator;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.domain.user.User;

public class UserPasswordValidator {

	public static void validate(String oldPassword, String password, User user) {
		if (oldPassword.equals(password)) {
			throw new CustomException(ErrorCode.SAME_OLD_PASSWORD);
		}
		if (!user.getPassword().equals(oldPassword)) {
			throw new CustomException(ErrorCode.INVALID_OLD_PASSWORD);
		}
	}
}
