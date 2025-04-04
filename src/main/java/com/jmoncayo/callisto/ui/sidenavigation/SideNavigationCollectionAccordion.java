package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationCollectionAccordion extends Accordion {

	public SideNavigationCollectionAccordion(CollectionController collectionController) {
		List<Collection> collections = collectionController.getAllCollections();
		for (Collection collection : collections) {
			VBox contentBox = new VBox(8); // spacing between subfolder items
			buildSubfolderTree(contentBox, collection, 0);
			TitledPane collectionPane = new TitledPane(collection.getName(), contentBox);
			this.getPanes().add(collectionPane);
		}
	}

	private void buildSubfolderTree(VBox parentBox, Collection current, int depth) {
		// Add current folder as a label
		if (depth > 0) { // skip top-level label since it's already the TitledPane
			HBox folderRow = new HBox(4);
			folderRow.setPadding(new Insets(0, 0, 0, depth * 15));
			folderRow.getChildren().add(new Label(current.getName()));
			parentBox.getChildren().add(folderRow);
		}

		// Add requests as buttons
		for (ApiRequest request : current.getRequests()) {
			HBox requestRow = new HBox(4);
			requestRow.setPadding(new Insets(0, 0, 0, (depth + 1) * 15)); // indent deeper than folder
			Button requestButton = new Button(request.getName());
			requestButton.setMaxWidth(Double.MAX_VALUE);
			requestButton.setOnAction(e -> {
				// Replace with your real trigger logic
				System.out.println("Clicked request: " + request.getName());
			});
			requestRow.getChildren().add(requestButton);
			parentBox.getChildren().add(requestRow);
		}

		// Recurse into subfolders
		for (Collection subfolder : current.getSubfolders()) {
			buildSubfolderTree(parentBox, subfolder, depth + 1);
		}
	}

	public void addNewCollection(Collection collection) {
		VBox contentBox = new VBox(8);
		buildSubfolderTree(contentBox, collection, 0);
		TitledPane pane = new TitledPane(collection.getName(), contentBox);
		this.getPanes().add(pane);
	}
}
