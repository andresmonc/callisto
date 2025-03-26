package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.ui.controllers.CollectionController;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationCollectionControls extends HBox {
	private Button newCollectionButton;

	public SideNavigationCollectionControls(CollectionController collectionController) {
		this.newCollectionButton = new Button("+");
		this.getChildren().add(newCollectionButton);
		var searchBar = new TextArea();
		searchBar.setPrefHeight(5);
		searchBar.setMinHeight(5);
		searchBar.setMinHeight(5);
		this.getChildren().add(searchBar);
	}

	public Button getNewCollectionButton() {
		return newCollectionButton;
	}
}
