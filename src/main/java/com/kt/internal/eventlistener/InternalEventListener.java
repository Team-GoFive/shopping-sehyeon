package com.kt.internal.eventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kt.common.support.VisitorEvent;
import com.kt.service.visitstat.VisitStatService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InternalEventListener {

	private final VisitStatService visitStatService;

	@Async
	@EventListener(VisitorEvent.class)
	public void onVisitorEvent(VisitorEvent event) {
		visitStatService.create(
			event.ip(),
			event.userAgent(),
			event.userId()
		);

		System.out.println(event);
	}
}
