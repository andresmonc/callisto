package com.jmoncayo.callisto.collection;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder(toBuilder = true)
@Jacksonized
public class Collection {
	@Builder.Default
	private String id = UUID.randomUUID().toString();

	private String name;
}
