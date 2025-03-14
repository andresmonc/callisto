package com.jmoncayo.callisto.config;

import javafx.scene.control.SplitPane;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.jmoncayo.callisto") // Replace with your package
public class AppConfig {

    @Bean
    public SplitPane splitPane() {
        return new SplitPane();
    }

}