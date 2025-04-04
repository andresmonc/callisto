package com.jmoncayo.callisto.ui.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LaunchRequestEvent extends ApplicationEvent {
	private final String requestId;

	public LaunchRequestEvent(Object source, String requestId) {
		super(source);
		this.requestId = requestId;
	}
}
