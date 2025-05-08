package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.environment.Environment;
import com.jmoncayo.callisto.environment.EnvironmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentController {
	private final ApplicationEventPublisher eventPublisher;
	private final EnvironmentService environmentService;

	@Autowired
	public EnvironmentController(ApplicationEventPublisher eventPublisher, EnvironmentService environmentService) {
		this.eventPublisher = eventPublisher;
		this.environmentService = environmentService;
	}

	public Environment getActiveEnvironment() {
		return environmentService.getActiveEnvironment();
	}

	public List<Environment> getAllEnvironments() {
		return environmentService.list();
	}

	public void createEnvironment(Environment environment) {
		environmentService.save(environment);
	}
}
