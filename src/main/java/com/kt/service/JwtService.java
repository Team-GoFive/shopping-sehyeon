package com.kt.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.kt.security.JwtProperties;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
	private final JwtProperties jwtProperties;

	// 토큰 발급 메서드
	public String issue(Long id, Date expiration) {
		// id 값은 jwt의 식별자 같은 개념 -> User의 id값
		// claims -> jwt안에 들어갈 정보를 Map형태로 넣는데 id, 1

		String token = Jwts.builder()
			.subject("kt-cloud-shopping")
			.issuer("terry")
			.issuedAt(new Date())
			.id(id.toString())
			.expiration(expiration) // 10분 유효기간
			.signWith(jwtProperties.getSecret())
			.compact();
		return token;
	}

	public Date getAccessExpiration() {
		return jwtProperties.getAccessTokenExpiration();
	}

	public Date getRefreshExpiration() {
		return jwtProperties.getRefreshTokenExpiration();
	}

}
