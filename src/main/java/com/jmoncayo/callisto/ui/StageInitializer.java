package com.jmoncayo.callisto.ui;

import com.jmoncayo.callisto.ui.controllers.MainController;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

	private final MainController mainController;

	@Autowired
	public StageInitializer(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		Stage stage = event.getStage();
		Scene scene = new Scene(mainController.getRoot(), 900, 700);
		scene.getStylesheets()
				.add(Objects.requireNonNull(getClass().getResource("/style.css"))
						.toExternalForm());
		stage.setTitle("Callisto");
		stage.getIcons().add(new Image("file:src/main/resources/logo.png"));
		stage.setScene(scene);
		stage.show();
	}
}
