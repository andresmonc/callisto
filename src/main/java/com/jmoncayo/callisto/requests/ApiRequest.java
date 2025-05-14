package com.jmoncayo.callisto.requests;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
@Getter
public class ApiRequest {
	@Builder.Default
	private final String id = UUID.randomUUID().toString();

	private final String method;
	private final String url;
	private final String body;
	private final List<Header> headers;
	private final boolean active;
	private final String name;
	private final List<Parameter> parameters;
	private final RequestSettings requestSettings;
	private final boolean parented;
	private final ApiRequest unsavedChanges;
	private final String collectionId;
}
