package com.jmoncayo.callisto.collection;

import com.jmoncayo.callisto.requests.ApiRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder(toBuilder = true)
@Jacksonized
public class Collection {
	@Builder.Default
	private final String id = UUID.randomUUID().toString();

	private final String name;

	@Builder.Default
	private final List<Collection> subfolders = new ArrayList<>();

	@Builder.Default
	private final List<ApiRequest> requests = new ArrayList<>();
}
