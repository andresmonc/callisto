package com.jmoncayo.callisto.ui.requestview;

import com.jmoncayo.callisto.ui.controllers.RequestController;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestView extends VBox {

    private final RequestController requestController;
    private final RequestField requestField;
    private final ResponseArea responseArea;

    // private final requestID ? each request view should be tied to a request ID
    //

    public RequestView(RequestController requestController, RequestField requestField, RequestDetails tabsComponent, ResponseArea responseArea) {
        this.requestController = requestController;
        this.requestField = requestField;
        this.responseArea = responseArea;
        // Set up the RequestField and TabsComponent in a VBox
        this.getChildren().addAll(requestField, tabsComponent);
        // Create a SplitPane for the resizable area (between RequestField/Tabs and Response area)
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        // Create the top section that contains RequestField and TabsComponent
        VBox topSection = new VBox();
        topSection.getChildren().addAll(requestField, tabsComponent);
        // Add the top section and response area to the SplitPane
        splitPane.getItems().addAll(topSection, responseArea);
        // Set the SplitPane's divider position to be resizeable (set divider position between 0.8 and 1.0)
        splitPane.setDividerPositions(0.8);
        // Add the SplitPane to the main VBox
        this.getChildren().add(splitPane);
        // Set the VBox to stretch in width
        this.setFillWidth(true);

        requestField.getActionButton().setOnAction(event -> handleRequest());
        requestField.getMethod().setOnAction(event -> requestController.updateRequestMethod(requestField.getMethod().getValue()));
        requestField.getRequestURL().textProperty().addListener((observable, oldValue, newValue) -> requestController.updateRequestUrl(newValue));
    }

    private void handleRequest() {
        String url = requestField.getRequestURL().getText();
        String method = requestField.getMethod().getValue();
        TextArea responseDisplay = responseArea.getResponseDisplay();
        if (url == null || url.isEmpty()) {
            responseDisplay.setText("Error: URL cannot be empty.");
            return;
        }
        if (method == null) {
            responseDisplay.setText("Error: Method must be selected.");
        }
        responseDisplay.setText("Sending request...");
        requestController.submitRequest()
                .subscribe(response -> Platform.runLater(() -> responseDisplay.setText(response)),
                        error -> Platform.runLater(() -> responseDisplay.setText("Error: " + error.getMessage())));
    }
}