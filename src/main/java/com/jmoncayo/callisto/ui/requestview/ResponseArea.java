package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class ResponseArea extends VBox {
    private final TextArea responseDisplay;

    public ResponseArea() {
        HBox responseAreaNav = new HBox();

        Text label = new Text("Response | ");
        ComboBox<String> dropdown = new ComboBox<>();
        dropdown.getItems().addAll("Option 1", "Option 2", "Option 3");
        this.responseDisplay = new TextArea("Send a request to display");
        responseDisplay.setEditable(false);
        responseDisplay.setMinHeight(0);
        responseDisplay.setPrefHeight(600);
        responseDisplay.setMaxWidth(Double.MAX_VALUE);
        responseAreaNav.getChildren().addAll(label, dropdown);
        this.getChildren().addAll(responseAreaNav, responseDisplay);
    }

    public TextArea getResponseDisplay() {
        return responseDisplay;
    }
}
