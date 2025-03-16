package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class AuthorizationTab extends Tab {
    public AuthorizationTab() {
        this.setText("Authorization");
        this.setClosable(false);
        this.setContent(new StackPane(new Label("Authorization")));
    }
}
