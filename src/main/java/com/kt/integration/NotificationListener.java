package com.kt.integration;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kt.common.Message;
import com.kt.integration.notify.DefaultNotifyApi;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {
	private final DefaultNotifyApi slackApi;

	@EventListener(Message.class)
	public void onMessage(Message message) {
		slackApi.notify("Received: " + message.message());
	}
}
