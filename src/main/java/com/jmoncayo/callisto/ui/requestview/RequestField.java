package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestField extends HBox {
    private final RequestURL requestURL;
    private final Button actionButton;
    private final ComboBox<String> dropdown;  // Dropdown (ComboBox) to the left of the TextField

    @Autowired
    public RequestField(RequestURL requestURL) {
        this.requestURL = requestURL;  // Initialize the RequestURL

        // Initialize the dropdown with some sample items
        dropdown = new ComboBox<>();
        dropdown.getItems().addAll("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS", "TRACE");
        dropdown.setValue("GET");  // Set default value

        // Create the action button
        actionButton = new Button("Submit");
        actionButton.setOnAction(event -> {
            // Logic for when the button is clicked
            System.out.println("Selected Option: " + dropdown.getValue());
            System.out.println("URL: " + requestURL.getText());
        });

        // Add the ComboBox (dropdown), TextField (RequestURL), and Button to the HBox
        this.getChildren().addAll(dropdown, requestURL, actionButton);
        this.setSpacing(10);  // Set spacing between elements
    }

    public RequestURL getRequestURL() {
        return requestURL;
    }

    public Button getActionButton() {
        return actionButton;
    }

    public ComboBox<String> getMethod() {
        return dropdown;
    }
}
