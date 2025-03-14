package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;


@Component
public class RequestURL extends TextField {
    public RequestURL() {
        this.setPromptText("Enter URL or paste text");
        this.setMinWidth(200);
        this.setWidth(200);
        this.getStyleClass().add("request-url");
        this.setVisible(true);
    }
}
