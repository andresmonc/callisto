package com.jmoncayo.callisto.ui.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RequestRenamedEvent extends ApplicationEvent {
	private final String requestId;
	private final String newName;

	public RequestRenamedEvent(Object source, String requestId, String newName) {
		super(source);
		this.requestId = requestId;
		this.newName = newName;
	}
}
