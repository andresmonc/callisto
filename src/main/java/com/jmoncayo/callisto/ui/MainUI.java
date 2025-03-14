package com.jmoncayo.callisto.ui;

import com.jmoncayo.callisto.config.AppConfig;
import com.jmoncayo.callisto.ui.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;

public class MainUI extends Application {

    private ApplicationContext context;

    @Override
    public void start(Stage primaryStage) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        // Create an instance of MainController
        MainController controller = context.getBean(MainController.class);

        // Set up the scene and stage
        Scene scene = new Scene(controller.getRoot(), 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        primaryStage.setTitle("Callisto");
        primaryStage.getIcons().add(new Image("file:src/main/resources/logo.png")); // Set window icon

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
