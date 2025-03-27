package com.jmoncayo.callisto.ui;

import com.jmoncayo.callisto.CallistoApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainUI extends Application {

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() {
		this.applicationContext = new SpringApplicationBuilder(CallistoApplication.class).run();
	}

	@Override
	public void start(Stage primaryStage) {
		applicationContext.publishEvent(new StageReadyEvent(primaryStage));
	}

	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}
}
