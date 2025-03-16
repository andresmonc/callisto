package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestDetails extends TabPane {
    public RequestDetails(List<Tab> tabs) {
        this.getTabs().addAll(tabs);
    }
}
