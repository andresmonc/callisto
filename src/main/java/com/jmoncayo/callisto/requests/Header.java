package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class Header {
	private String key;
	private String value;
	private String description;
	private boolean disabled;
}
