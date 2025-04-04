package com.jmoncayo.callisto.ui.events;

import com.jmoncayo.callisto.requests.ApiRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RequestAddedToCollectionEvent extends ApplicationEvent {

	private final ApiRequest request;
	private final String collectionId;

	public RequestAddedToCollectionEvent(Object source, ApiRequest request, String collectionId) {
		super(source);
		this.request = request;
		this.collectionId = collectionId;
	}
}
