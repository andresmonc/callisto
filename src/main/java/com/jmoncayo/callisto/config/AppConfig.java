package com.jmoncayo.callisto.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.jmoncayo.callisto")
@PropertySource("classpath:application.properties")
public class AppConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
