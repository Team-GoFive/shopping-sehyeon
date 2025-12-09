package com.kt.integration.notify;

import org.springframework.core.env.Environment;

import com.slack.api.methods.MethodsClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
// @AppProfile // prod, dev
@RequiredArgsConstructor
public class AppNotifyApi implements NotifyApi {
	private final MethodsClient methodsClient;
	private final SlackProperties slackProperties;
	private final Environment environment;

	@Override
	public void notify(String message) {
		// 실제로 슬랙으로 알림을 보낼 것
		// 슬랙 채널에 보내주는 메세지 - dev, prod 환경에서만 발송
		try {
			methodsClient.chatPostMessage(request -> {
				request.username("spring-bot")
					.channel(slackProperties.logChannel())
					.text(String.format("```%s - shopping-%s```", message, getActiveProfile()))
					.build();
				return request;
			});
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private String getActiveProfile() {
		return environment.getProperty("SPRING_PROFILES_ACTIVE");
	}
}
