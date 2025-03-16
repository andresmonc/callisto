package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SideNavigationCollectionAccordion extends Accordion {

    private final CollectionController collectionController;

    public SideNavigationCollectionAccordion(CollectionController collectionController) {
        this.collectionController = collectionController;

        List<Collection> collections = collectionController.getCollections();
        List<TitledPane> panes = new ArrayList<>(collections.size());
        for (Collection collection : collections) {
            TitledPane collectionPane = new TitledPane(collection.getName(), new StackPane());
            collectionPane.setContent(new StackPane());
            panes.add(collectionPane);
        }
        // test
        TitledPane section1 = new TitledPane("Section 1", new StackPane());
        section1.setContent(new StackPane(new Button("Test")));
        panes.add(section1);
        // end test
        this.getPanes().addAll(panes);
    }
}
