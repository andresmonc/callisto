package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class Parameter implements KeyValueDescription {
	private String key;
	private String value;
	private String description;
	private boolean enabled;
	private boolean placeholder;
}
