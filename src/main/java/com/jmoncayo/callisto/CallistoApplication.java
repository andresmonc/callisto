package com.jmoncayo.callisto;

import com.jmoncayo.callisto.ui.MainUI;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CallistoApplication {

	public static void main(String[] args) {
		//        new SpringApplicationBuilder(CallistoApplication.class).web(WebApplicationType.NONE).run();
		// Launch JavaFX after Spring Boot context is initialized
		Application.launch(MainUI.class, args);
	}
}
