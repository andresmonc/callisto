package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationCollectionAccordion extends Accordion {

	public SideNavigationCollectionAccordion(CollectionController collectionController) {
		List<Collection> collections = collectionController.getAllCollections();
		for (Collection collection : collections) {
			VBox contentBox = new VBox(8); // spacing between subfolder items
			buildSubfolderTree(contentBox, collection.getSubfolders(), 0);

			TitledPane collectionPane = new TitledPane(collection.getName(), contentBox);
			this.getPanes().add(collectionPane);
		}
	}

	private void buildSubfolderTree(VBox parentBox, List<Collection> subfolders, int depth) {
		for (Collection subfolder : subfolders) {
			HBox row = new HBox(4);
			row.setPadding(new Insets(0, 0, 0, depth * 15)); // indent based on depth
			Label label = new Label(subfolder.getName());
			row.getChildren().add(label);

			parentBox.getChildren().add(row);

			// Recursively build children
			buildSubfolderTree(parentBox, subfolder.getSubfolders(), depth + 1);
		}
	}

	public void addNewCollection(Collection collection) {
		VBox contentBox = new VBox(8);
		buildSubfolderTree(contentBox, collection.getSubfolders(), 0);

		TitledPane pane = new TitledPane(collection.getName(), contentBox);
		this.getPanes().add(pane);
	}
}

