package com.jmoncayo.callisto.environment;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EnvironmentRepository {

    private final Map<String, Environment> environments = new ConcurrentHashMap<>();

    public Optional<Environment> findByName(String name) {
        return Optional.ofNullable(environments.get(name));
    }

    public List<Environment> findAll() {
        return new ArrayList<>(environments.values());
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