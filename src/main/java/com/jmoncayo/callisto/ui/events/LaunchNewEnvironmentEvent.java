package com.jmoncayo.callisto.ui.events;

import org.springframework.context.ApplicationEvent;

public class LaunchNewEnvironmentEvent extends ApplicationEvent {
	public LaunchNewEnvironmentEvent(Object source) {
		super(source);
	}
}
