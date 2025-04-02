package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationCollectionView extends VBox {
	public SideNavigationCollectionView(
			CollectionController collectionController,
			SideNavigationCollectionControls sideNavigationCollectionControls,
			SideNavigationCollectionAccordion sideNavigationCollectionAccordion) {
		this.getChildren().add(sideNavigationCollectionControls);
		this.getChildren().add(sideNavigationCollectionAccordion);
		sideNavigationCollectionControls.getNewCollectionButton().setOnAction(event -> {
			Collection collection = collectionController.addCollection("Untitled Collection");
			sideNavigationCollectionAccordion.addNewCollection(collection);
		});
	}
}
