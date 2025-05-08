package com.jmoncayo.callisto.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class EnvironmentRepository {

	private final Map<String, Environment> environments = new ConcurrentHashMap<>();
	// Default environment
	private static final Environment defaultEnvironment =
			Environment.builder().name("No Environment").active(true).build();

	public Optional<Environment> findByName(String name) {
		return Optional.ofNullable(environments.get(name));
	}

	public List<Environment> findAll() {
		ArrayList<Environment> environmentsResponse = new ArrayList<>(environments.values());
		environmentsResponse.add(defaultEnvironment);
		return environmentsResponse;
	}

	public Environment findActive() {
		return environments.values().stream()
				.filter(Environment::isActive)
				.findFirst()
				.orElse(defaultEnvironment);
	}

	public void save(Environment environment) {
		environments.put(environment.getName(), environment);
	}

	public void delete(String name) {
		environments.remove(name);
	}

	public void clear() {
		environments.clear();
	}

	public boolean exists(String name) {
		return environments.containsKey(name);
	}
}
