package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.ui.controllers.CollectionController;
import javafx.scene.layout.VBox;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationCollectionView extends VBox {
	public SideNavigationCollectionView(
			CollectionController collectionController,
			SideNavigationCollectionControls sideNavigationCollectionControls,
			SideNavigationCollectionTreeView sideNavigationCollectionAccordion) {
		this.getChildren().add(sideNavigationCollectionControls);
		this.getChildren().add(sideNavigationCollectionAccordion);
		sideNavigationCollectionControls.getNewCollectionButton().setOnAction(event -> collectionController.addCollection("Untitled Collection"));
	}
}
