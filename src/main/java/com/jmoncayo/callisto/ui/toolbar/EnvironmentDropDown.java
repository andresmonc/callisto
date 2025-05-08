package com.jmoncayo.callisto.ui.toolbar;

import com.jmoncayo.callisto.environment.Environment;
import com.jmoncayo.callisto.ui.controllers.EnvironmentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnvironmentDropDown extends StackPane {

    private final EnvironmentController environmentController;
    private final Button dropdownButton = new Button("Select Environment");
    private final Popup popup = new Popup();
    private final TextField searchField = new TextField();
    private final Button addButton = new Button("+");
    private final ListView<String> listView = new ListView<>();
    private final ObservableList<String> allItems = FXCollections.observableArrayList();

    @Autowired
    public EnvironmentDropDown(EnvironmentController environmentController) {
        this.environmentController = environmentController;

        // Load data
        List<String> envNames = environmentController.getAllEnvironments().stream()
                .map(Environment::getName).toList();
        allItems.setAll(envNames);
        listView.setItems(FXCollections.observableArrayList(envNames));

        // Active environment name
        Environment activeEnvironment = environmentController.getActiveEnvironment();
        dropdownButton.setText(activeEnvironment.getName() + " ▼");

        // Layout for search + button
        searchField.setPromptText("Search...");
        searchField.setPrefWidth(160);
        addButton.setPrefWidth(30);
        addButton.setFocusTraversable(false);
        HBox searchBox = new HBox(5, searchField, addButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        // Full popup container
        VBox container = new VBox(5, searchBox, listView);
        container.setStyle("-fx-background-color: white; -fx-padding: 10;");
        container.setPrefWidth(220);
        container.setPrefHeight(250);
        popup.getContent().add(container);

        // Show popup below button
        dropdownButton.setOnAction(e -> {
            if (!popup.isShowing()) {
                Point2D point = dropdownButton.localToScreen(0, dropdownButton.getHeight());
                popup.show(dropdownButton, point.getX(), point.getY());
                searchField.requestFocus();
            } else {
                popup.hide();
            }
        });

        // Filter items on search
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String filter = newVal.toLowerCase();
            listView.setItems(allItems.filtered(item -> item.toLowerCase().contains(filter)));
        });

        // Select from list
        listView.setOnMouseClicked(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                dropdownButton.setText(selected);
                popup.hide();
            }
        });

        // Add button logic
        addButton.setOnAction(e -> {
            String newEnvName = searchField.getText().trim();
            if (!newEnvName.isEmpty() && !allItems.contains(newEnvName)) {
                allItems.add(newEnvName);
                FXCollections.sort(allItems);
                listView.setItems(allItems.filtered(item -> item.toLowerCase().contains(newEnvName.toLowerCase())));
                dropdownButton.setText(newEnvName);
                popup.hide();
            }
        });

        this.getChildren().add(dropdownButton);
    }

    public String getSelectedEnvironment() {
        return dropdownButton.getText();
    }
}
