package com.jmoncayo.callisto.ui.sidenavigation;

import org.springframework.context.ApplicationEvent;

public class LaunchRequest extends ApplicationEvent {

    public LaunchRequest(String requestId) {
        super(requestId);
    }
}
