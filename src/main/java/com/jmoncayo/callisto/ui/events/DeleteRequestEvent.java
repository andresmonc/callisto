package com.jmoncayo.callisto.ui.events;

import org.springframework.context.ApplicationEvent;

public class DeleteRequestEvent extends ApplicationEvent {
    private final String id;

    public DeleteRequestEvent(Object source, String id) {
        super(source);
        this.id = id;
    }
}
