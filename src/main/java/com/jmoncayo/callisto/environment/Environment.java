package com.jmoncayo.callisto.environment;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Environment {
	private String name;
	private String baseUrl;
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> variables = new HashMap<>();

	public void addVariable(String key, String value) {
		variables.put(key, value);
	}

	public void deleteVariable(String key) {
		variables.remove(key);
	}

	public String getVariable(String key) {
		return variables.get(key);
	}
}
