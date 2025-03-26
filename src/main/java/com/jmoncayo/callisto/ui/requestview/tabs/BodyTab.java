package com.jmoncayo.callisto.ui.requestview.tabs;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Order(5)
public class BodyTab extends Tab {
	public BodyTab() {
		this.setText("Body");
		this.setClosable(false);
		this.setContent(new StackPane(new Label("Body")));
	}
}
