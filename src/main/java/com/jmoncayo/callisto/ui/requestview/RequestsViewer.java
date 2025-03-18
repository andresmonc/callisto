package com.jmoncayo.callisto.ui.requestview;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestsViewer extends AnchorPane {

    public RequestsViewer(ObjectFactory<RequestView> requestView) {
        final TabPane tabs = new TabPane();
        final Button addButton = new Button("+");
        addButton.setOnAction(event -> {
            var tab = new Tab("Untitled");
            tab.setContent(requestView.getObject());
            tabs.getTabs().add(tab);
        });
        AnchorPane.setTopAnchor(tabs, 5.0);
        AnchorPane.setLeftAnchor(tabs, 5.0);
        AnchorPane.setRightAnchor(tabs, 5.0);
        AnchorPane.setTopAnchor(addButton, 10.0);
        AnchorPane.setRightAnchor(addButton, 10.0);
        var tab = new Tab("Untitled");
        tab.setContent(requestView.getObject());
        tabs.getTabs().add(tab);
        this.getChildren().addAll(tabs, addButton);

        // update request controller with active request
//        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
//            if (newTab != null) {
//                System.out.println("Active tab changed to: " + newTab.getText());
//            }
//        });
    }

}
