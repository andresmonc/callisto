package com.jmoncayo.callisto.environment;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnvironmentService {

	private final EnvironmentRepository repository;

	public Environment get(String name) {
		return repository
				.findByName(name)
				.orElseThrow(() -> new NoSuchElementException("No environment named: " + name));
	}

	public List<Environment> list() {
		return repository.findAll();
	}

	public void save(Environment environment) {
		repository.save(environment);
	}

	public void delete(String name) {
		if (!repository.exists(name)) {
			throw new NoSuchElementException("No environment to delete: " + name);
		}
		repository.delete(name);
	}

	public boolean exists(String name) {
		return repository.exists(name);
	}

	public Environment getActiveEnvironment() {
		return repository.findActive();
	}
}
