package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Header {
	private String key;
	private String value;
	private String description;
	private boolean disabled;

	public Header() {}

	public Header(String key, String value, String description, boolean disabled) {
		this.key = key;
		this.value = value;
		this.description = description;
		this.disabled = disabled;
	}
}
