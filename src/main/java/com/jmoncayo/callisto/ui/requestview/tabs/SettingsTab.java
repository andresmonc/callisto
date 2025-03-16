package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(6)
public class SettingsTab extends Tab {
    public SettingsTab() {
        this.setText("Settings");
        this.setClosable(false);
        this.setContent(new StackPane(new Label("Settings")));
    }
}
