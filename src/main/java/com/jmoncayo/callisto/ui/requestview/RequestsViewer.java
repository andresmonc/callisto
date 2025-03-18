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
        addButton.setOnAction(event -> tabs.getTabs().add(emptyTab()));
        AnchorPane.setTopAnchor(tabs, 5.0);
        AnchorPane.setLeftAnchor(tabs, 5.0);
        AnchorPane.setRightAnchor(tabs, 5.0);
        AnchorPane.setTopAnchor(addButton, 10.0);
        AnchorPane.setRightAnchor(addButton, 10.0);
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

    private Tab newTab(ApiRequest request){
        var tab = new Tab(request.getName());
        RequestView requestView = requestViewObjectFactory.getObject();
        requestView.setRequestUUID(request.getId());
        tab.setOnClosed(event -> {
            requestController.closeRequest(requestView.getRequestUUID());
        });
        tab.setContent(requestView);
        return tab;
    }

    private Tab emptyTab() {
        var tab = new Tab("Untitled");
        tab.setContent(requestViewObjectFactory.getObject());
        return tab;
    }



}
