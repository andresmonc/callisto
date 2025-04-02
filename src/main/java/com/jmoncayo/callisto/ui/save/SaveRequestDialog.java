package com.jmoncayo.callisto.ui.save;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SaveRequestDialog {
	private final ListView<String> collectionList;
	private final TextField newCollectionField;
	private final CollectionController collectionController;
	private Stage stage;

	// Inject the service to fetch collections
	public SaveRequestDialog(CollectionController collectionController) {
		this.collectionController = collectionController;
		collectionList = new ListView<>();
		newCollectionField = new TextField();
		newCollectionField.setPromptText("New collection/subfolder name");
	}

	public void open(Stage owner) {
		// Create and configure the Stage here
		stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(owner);
		stage.setTitle("Save Request");

		// Create the save/cancel buttons
		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> {
			stage.close();
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(event -> {
			stage.close();
		});

		// Create the "New Collection" button
		Button newCollectionButton = new Button("New Collection");
		newCollectionButton.setOnAction(event -> {
			// Add an empty string to the ListView for the user to edit
			collectionList.getItems().add("");
			collectionList.getSelectionModel().selectLast(); // Focus the new entry for immediate editing
			collectionList.edit(collectionList.getItems().size() - 1); // Explicitly start editing the newly added item
		});

		// Layout and scene setup
		VBox layout = new VBox(
				10,
				new Label("Select a collection or create a new one:"),
				collectionList,
				newCollectionButton, // Add the "New Collection" button
				saveButton,
				cancelButton);
		layout.setPadding(new Insets(10));
		stage.setScene(new Scene(layout, 400, 300));

		// Load collections and show the dialog
		List<String> collections = collectionController.getCollections().stream()
				.map(Collection::getName)
				.toList();
		collectionList.getItems().setAll(collections);
		collectionList.setOnEditCommit(event -> {
			String newValue = event.getNewValue();
			if (newValue == null || newValue.isEmpty()) {
				collectionList.getItems().remove(event.getIndex());
				return;
			}
			collectionController.addCollection(newValue);
			collectionList.getItems().set(event.getIndex(), event.getNewValue());
		});

		// Enable editing in the ListView with TextFieldListCell
		collectionList.setEditable(true);
		collectionList.setCellFactory(TextFieldListCell.forListView());
		stage.showAndWait();
	}
}
