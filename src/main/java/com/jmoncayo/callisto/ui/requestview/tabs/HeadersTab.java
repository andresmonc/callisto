package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@Order(3)
public class HeadersTab extends Tab {
    public HeadersTab(HeadersTabView headersTabView) {
        this.setText("Headers");
        this.setClosable(false);
        this.setContent(headersTabView);
    }
}
