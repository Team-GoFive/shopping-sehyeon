package com.kt.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class TechUpAuthenticationToken extends AbstractAuthenticationToken {
	private final DefaultCurrentUser defaultCurrentUser;

	public TechUpAuthenticationToken(
		DefaultCurrentUser defaultCurrentUser,
		Collection<? extends GrantedAuthority> authorities
	) {
		super(authorities);
		this.setAuthenticated(true);
		this.defaultCurrentUser = defaultCurrentUser;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}
}
