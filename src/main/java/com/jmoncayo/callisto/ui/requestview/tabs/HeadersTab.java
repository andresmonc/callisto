package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class HeadersTab extends Tab {
    public HeadersTab() {
        this.setText("Headers");
        this.setClosable(false);
        this.setContent(new StackPane(new Label("Headers")));
    }
}
