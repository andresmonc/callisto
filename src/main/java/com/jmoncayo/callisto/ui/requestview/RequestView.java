package com.jmoncayo.callisto.ui.requestview;

import com.jmoncayo.callisto.ui.controllers.RequestController;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class RequestView extends VBox {

    private final RequestController requestController;
    private final RequestField requestField;
    private final TextArea responseDisplay;

    public RequestView(RequestController requestController, RequestField requestField, RequestDetails tabsComponent) {
        this.requestController = requestController;
        this.requestField = requestField;

        // Set up the RequestField and TabsComponent in a VBox
        this.getChildren().addAll(requestField, tabsComponent);

        // Create a SplitPane for the resizable area (between RequestField/Tabs and Response area)
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        // Create the top section that contains RequestField and TabsComponent
        VBox topSection = new VBox();
        topSection.getChildren().addAll(requestField, tabsComponent);

        // Create the response section with the label and dropdown
        VBox responseArea = new VBox();
        HBox responseAreaNav = new HBox();

        Text label = new Text("Response | ");
        ComboBox<String> dropdown = new ComboBox<>();
        dropdown.getItems().addAll("Option 1", "Option 2", "Option 3");
        this.responseDisplay = new TextArea("Send a request to display");
        responseDisplay.setEditable(false);
        responseDisplay.setMinHeight(0);
        responseDisplay.setPrefHeight(600);
        responseDisplay.setMaxWidth(Double.MAX_VALUE);
        responseAreaNav.getChildren().addAll(label,dropdown);
        responseArea.getChildren().addAll(responseAreaNav, responseDisplay);

        // Add the top section and response area to the SplitPane
        splitPane.getItems().addAll(topSection, responseArea);

        // Set the SplitPane's divider position to be resizeable (set divider position between 0.8 and 1.0)
        splitPane.setDividerPositions(0.8);  // Adjust the divider position to your preference

        // Add the SplitPane to the main VBox
        this.getChildren().add(splitPane);

        // Set the VBox to stretch in width
        this.setFillWidth(true);

        requestField.getActionButton().setOnAction(event -> handleRequest());
    }

    private void handleRequest() {
        String url = requestField.getRequestURL().getText();
        String method = requestField.getMethod().getValue();
        if (url == null || url.isEmpty()) {
            responseDisplay.setText("Error: URL cannot be empty.");
            return;
        }
        if (method == null) {
            responseDisplay.setText("Error: Method must be selected.");
        }
        responseDisplay.setText("Sending request...");

        requestController.submitRequest(url, method)
                .subscribe(response -> Platform.runLater(() -> responseDisplay.setText(response)),
                        error -> Platform.runLater(() -> responseDisplay.setText("Error: " + error.getMessage())));
    }
}