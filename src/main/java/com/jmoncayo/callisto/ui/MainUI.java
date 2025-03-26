package com.jmoncayo.callisto.ui;

import com.jmoncayo.callisto.config.AppConfig;
import com.jmoncayo.callisto.ui.controllers.MainController;
import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainUI extends Application {

	private AnnotationConfigApplicationContext context;

	@Override
	public void start(Stage primaryStage) {
		context = new AnnotationConfigApplicationContext(AppConfig.class);
		// Create an instance of MainController
		MainController controller = context.getBean(MainController.class);

		// Set up the scene and stage
		Scene scene = new Scene(controller.getRoot(), 800, 600);
		scene.getStylesheets()
				.add(Objects.requireNonNull(getClass().getResource("/style.css"))
						.toExternalForm());

		primaryStage.setTitle("Callisto");
		primaryStage.getIcons().add(new Image("file:src/main/resources/logo.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		// Shutdown Spring
		super.stop();
		if (context != null) {
			context.close();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
