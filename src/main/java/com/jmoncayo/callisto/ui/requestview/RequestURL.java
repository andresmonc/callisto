package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;


@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestURL extends TextField {
    public RequestURL() {
        this.setPromptText("Enter URL or paste text");
        this.setMinWidth(200);
        this.setWidth(200);
        this.getStyleClass().add("request-url");
        this.setVisible(true);
    }
}
