package com.kt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;

// @EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication
@RequiredArgsConstructor
public class ShoppingApplication {
	private final ApplicationEventPublisher publisher;

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	public void started() {
		// publisher.publishEvent(new Message("Shopping Application Started"));
	}

}
