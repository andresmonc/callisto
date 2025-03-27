package com.jmoncayo.callisto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter {
	private String key;
	private String value;
	private String description;
	private boolean enabled;
}
