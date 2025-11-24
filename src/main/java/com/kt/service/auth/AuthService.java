package com.kt.service.auth;

import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.exception.CustomException;
import com.kt.common.exception.ErrorCode;
import com.kt.common.support.PreConditions;
import com.kt.domain.user.User;
import com.kt.repository.UserRepository;
import com.kt.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public Pair<String, String> login(String loginId, String password) {
		// 아이디 존재 검사
		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new CustomException(ErrorCode.FAIL_LOGIN));

		// 비밀번호 검사
		PreConditions.validate(passwordEncoder.matches(password, user.getPassword()), ErrorCode.FAIL_LOGIN);

		// 로그인 성공 처리
		// jwt 토큰 발급
		String accessToken = jwtService.issue(user.getId(), jwtService.getAccessExpiration());
		String refreshToken = jwtService.issue(user.getId(), jwtService.getRefreshExpiration());
		return Pair.of(accessToken, refreshToken);
	}
}
