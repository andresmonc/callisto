package com.jmoncayo.callisto.ui.save;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.collection.CollectionService;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SaveRequestDialog {
	private final CollectionService collectionService;
	private String selectedPath;
	private final ListView<String> collectionList;
	private final TextField newCollectionField;
	private Stage stage;

	// Inject the service to fetch collections
	public SaveRequestDialog(CollectionService collectionService) {
		this.collectionService = collectionService;
		collectionList = new ListView<>();
		newCollectionField = new TextField();
		newCollectionField.setPromptText("New collection/subfolder name");
	}

	public void open(Stage owner) {
		// Make sure to run on the JavaFX thread
		Platform.runLater(() -> {
			// Create and configure the Stage here
			stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(owner);
			stage.setTitle("Save Request");

			// Create the save/cancel buttons
			Button saveButton = new Button("Save");
			saveButton.setOnAction(event -> {
				selectedPath = newCollectionField.getText().isEmpty()
						? collectionList.getSelectionModel().getSelectedItem()
						: newCollectionField.getText();
				stage.close();
			});

			Button cancelButton = new Button("Cancel");
			cancelButton.setOnAction(event -> {
				selectedPath = null;
				stage.close();
			});

			// Layout and scene setup
			VBox layout = new VBox(
					10,
					new Label("Select a collection or create a new one:"),
					collectionList,
					newCollectionField,
					saveButton,
					cancelButton);
			layout.setPadding(new Insets(10));
			stage.setScene(new Scene(layout, 400, 300));

			// Load collections and show the dialog
			List<String> collections = collectionService.getCollections().stream()
					.map(Collection::getName)
					.toList();
			collectionList.getItems().setAll(collections);
			selectedPath = null; // Reset any previous selection
			stage.showAndWait();
		});
	}

	public String getSelectedPath() {
		return selectedPath;
	}
}
