package com.jmoncayo.callisto.ui.requestview;

import com.jmoncayo.callisto.ui.controllers.RequestController;
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

    public RequestView(RequestController requestController, RequestField requestField, RequestDetails tabsComponent) {

        // Set up the RequestField and TabsComponent in a VBox
        this.getChildren().addAll(requestField, tabsComponent);

        // Create a SplitPane for the resizable area (between RequestField/Tabs and Response area)
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        // Create the top section that contains RequestField and TabsComponent
        VBox topSection = new VBox();
        topSection.getChildren().addAll(requestField, tabsComponent);

        // Create the response section with the label and dropdown
        HBox responseArea = new HBox();
        Text label = new Text("Response | ");
        ComboBox<String> dropdown = new ComboBox<>();
        dropdown.getItems().addAll("Option 1", "Option 2", "Option 3");
        var responseDisplay = new TextArea("asdfjklasdjklfasdf");
        responseDisplay.setMinHeight(0);
        responseDisplay.setPrefHeight(600);
        responseArea.getChildren().addAll(label, dropdown, responseDisplay);

        // Add the top section and response area to the SplitPane
        splitPane.getItems().addAll(topSection, responseArea);

        // Set the SplitPane's divider position to be resizeable (set divider position between 0.8 and 1.0)
        splitPane.setDividerPositions(0.8);  // Adjust the divider position to your preference

        // Add the SplitPane to the main VBox
        this.getChildren().add(splitPane);

        // Set the VBox to stretch in width
        this.setFillWidth(true);


        // request submission

        requestField.getActionButton().setOnAction(event -> {
            responseDisplay.setText(requestController.submitRequest(requestField.getRequestURL().getText(),requestField.getMethod().getValue()));
        });
    }
}