package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

@Component
public class RequestDetails extends TabPane {
    public RequestDetails() {
        // Create individual tabs
        Tab params = new Tab("Params");
        params.setClosable(false);
        params.setContent(new StackPane(new Label("Content for Tab 1")));  // Replace with your actual view content

        Tab authorization = new Tab("Authorization");
        authorization.setClosable(false);
        authorization.setContent(new StackPane(new Label("Content for Tab 2")));  // Replace with your actual view content

        Tab headers = new Tab("Headers");
        headers.setClosable(false);
        headers.setContent(new StackPane(new Label("Content for Tab 4")));  // Replace with your actual view content

        Tab body = new Tab("Body");
        body.setClosable(false);
        body.setContent(new StackPane(new Label("Content for Tab 5")));  // Replace with your actual view content

        Tab scripts = new Tab("Scripts");
        scripts.setClosable(false);
        scripts.setContent(new StackPane(new Label("Content for Tab 6")));  // Replace with your actual view content

        Tab settings = new Tab("Settings");
        settings.setClosable(false);
        settings.setContent(new StackPane(new Label("Content for Tab 7")));  // Replace with your actual view content

        // Add the tabs to the TabPane
        this.getTabs().addAll(params, authorization, headers, body);
    }
}
