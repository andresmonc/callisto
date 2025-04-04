package com.jmoncayo.callisto.ui.sidenavigation;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LaunchRequest extends ApplicationEvent {
	private final String requestId;

	public LaunchRequest(Object source, String requestId) {
		super(source);
		this.requestId = requestId;
	}
}
