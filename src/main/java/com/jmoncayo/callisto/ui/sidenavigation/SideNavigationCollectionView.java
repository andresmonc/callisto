package com.jmoncayo.callisto.ui.sidenavigation;

import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationCollectionView extends VBox {
    public SideNavigationCollectionView(SideNavigationCollectionAccordion sideNavigationCollectionAccordion) {
        this.getChildren().add(sideNavigationCollectionAccordion);
    }
}
