package com.jmoncayo.callisto.ui.requestview;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.ui.controllers.RequestController;
import jakarta.annotation.PostConstruct;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestsViewer extends AnchorPane {
    private final TabPane tabs = new TabPane();
    private final ObjectFactory<RequestView> requestViewObjectFactory;
    private final RequestController requestController;

    @Autowired
    public RequestsViewer(ObjectFactory<RequestView> requestViewObjectFactory, RequestController requestController) {
        this.requestViewObjectFactory = requestViewObjectFactory;
        this.requestController = requestController;
        final Button addButton = new Button("+");
        addButton.getStyleClass().add("request-tab-pane-add-button");
        addButton.setOnAction(event -> tabs.getTabs().add(emptyTab()));
        AnchorPane.setLeftAnchor(tabs, 0.0);
        AnchorPane.setRightAnchor(tabs, 0.0);
        AnchorPane.setTopAnchor(addButton, 2.5);
        AnchorPane.setRightAnchor(addButton, 5.0);
        this.getChildren().addAll(tabs, addButton);
    }

    @PostConstruct
    public void loadRequests() {
        List<ApiRequest> activeRequests = requestController.getActiveRequests();
        activeRequests.forEach(request -> {
            Tab tab = newTab(request);
            tabs.getTabs().add(tab);
        });
        if (activeRequests.isEmpty()) {
            tabs.getTabs().add(emptyTab());
        }
    }

    private Tab createTab(ApiRequest request) {
        var tab = new Tab(request.getName() != null ? request.getName() : "Untitled");
        RequestView requestView = requestViewObjectFactory.getObject();
        requestView.setRequestUUID(request.getId());
        tab.setContent(requestView);
        tab.setOnClosed(event -> requestController.closeRequest(requestView.getRequestUUID()));
        return tab;
    }

    private Tab newTab(ApiRequest request) {
        return createTab(request);
    }

    private Tab emptyTab() {
        ApiRequest request = requestController.createRequest();
        return createTab(request);
    }

}
