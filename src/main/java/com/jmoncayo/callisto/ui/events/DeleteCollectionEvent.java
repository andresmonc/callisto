package com.jmoncayo.callisto.ui.events;

import org.springframework.context.ApplicationEvent;

public class DeleteCollectionEvent extends ApplicationEvent {
    private final String id;

    public DeleteCollectionEvent(Object source, String id) {
        super(source);
        this.id = id;
    }
}
