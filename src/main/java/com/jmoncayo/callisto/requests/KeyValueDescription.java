package com.jmoncayo.callisto.requests;

public interface KeyValueDescription {
	String getKey();

	String getValue();

	String getDescription();

	boolean isEnabled();

	boolean isPlaceholder();
}
