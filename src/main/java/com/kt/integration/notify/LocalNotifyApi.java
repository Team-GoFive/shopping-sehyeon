package com.kt.integration.notify;

import org.springframework.stereotype.Component;

import com.kt.common.profile.LocalProfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@LocalProfile
public class LocalNotifyApi implements NotifyApi {
	// 로컬에서는 그냥 로그만 남김

	@Override
	public void notify(String message) {
		log.info(message);
	}
}
