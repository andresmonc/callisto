package com.jmoncayo.callisto.ui.requestview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestURL extends TextField {
	public RequestURL() {
		this.setPromptText("Enter URL or paste text");
		this.setMinWidth(400);
		this.setWidth(400);
		this.getStyleClass().add("request-url");
		this.setVisible(true);
	}
}
