package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestDetails extends TabPane {
    public RequestDetails(List<Tab> tabs) {
        this.getTabs().addAll(tabs);
    }
}
