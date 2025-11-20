package com.kt.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtProperties {

	private final String secret; // 최소 256bit 이상
	private final Long accessTokenExpiration; // 밀리초 단위
	private final Long refreshTokenExpiration; // 밀리초 단위

	public Date getAccessTokenExpiration() {
		return new Date(new Date().getTime() + accessTokenExpiration);
	}

	public Date getRefreshTokenExpiration() {
		return new Date(new Date().getTime() + refreshTokenExpiration);
	}

	public SecretKey getSecret() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
}
