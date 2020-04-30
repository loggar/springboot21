package com.loggar.springboot21.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.loggar.springboot21.domain.sse.NotificationMsg;

@Controller
public class SseNotificationMsgController {
	private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	@GetMapping("/subscribe-notification-message")
	public SseEmitter handle(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");

		// keep connection open for 180 seconds
		SseEmitter emitter = new SseEmitter(180_000L);
		this.emitters.add(emitter);

		emitter.onCompletion(() -> this.emitters.remove(emitter));
		emitter.onTimeout(() -> this.emitters.remove(emitter));

		System.out.println("[log] new emitter: " + emitter);
		return emitter;
	}

	@EventListener
	public void onNotificationMessage(NotificationMsg notificationMessage) {
		List<SseEmitter> deadEmitters = new ArrayList<>();

		System.out.println("[log] emitters: " + this.emitters.size());

		this.emitters.forEach(emitter -> {
			try {
				emitter.send(notificationMessage);
			} catch (Exception e) {
				deadEmitters.add(emitter);
				System.out.println("[log] dead emitters: " + this.emitters);
			}
		});

		this.emitters.removeAll(deadEmitters);
	}
}