package com.jmoncayo.callisto.ui.events;

import com.jmoncayo.callisto.collection.Collection;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewCollectionEvent extends ApplicationEvent {
    private final Collection collection;

    public NewCollectionEvent(Object source, Collection collection) {
        super(source);
        this.collection = collection;
    }
}
