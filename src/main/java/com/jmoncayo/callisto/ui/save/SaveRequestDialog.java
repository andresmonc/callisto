package com.jmoncayo.callisto.ui.save;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import java.util.List;

import com.jmoncayo.callisto.ui.controllers.RequestController;
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
	private final RequestController requestController;
	private Stage stage;
	private String selectedCollection;

	// Inject the service to fetch collections
	public SaveRequestDialog(CollectionController collectionController, RequestController requestController) {
		this.collectionController = collectionController;
		this.requestController = requestController;
		collectionList = new ListView<>();
		newCollectionField = new TextField();
		newCollectionField.setPromptText("New collection/subfolder name");
	}

	public void open(Stage owner) {
		stage = createStage(owner);
		VBox layout = createLayout();
		setUpCollectionList();
		setUpButtons();
		stage.setScene(new Scene(layout, 400, 300));
		stage.showAndWait();
	}

	private Stage createStage(Stage owner) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(owner);
		newStage.setTitle("Save Request To Collection");
		return newStage;
	}

	private VBox createLayout() {
		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");
		Button newCollectionButton = new Button("New Collection");
		saveButton.setOnAction(event -> saveRequest());
		cancelButton.setOnAction(event -> stage.close());
		newCollectionButton.setOnAction(event -> createNewCollection());
		return new VBox(10,
				new Label("Select a collection or create a new one:"),
				collectionList,
				newCollectionButton,
				saveButton,
				cancelButton);
	}

	private void setUpCollectionList() {
		List<String> collections = collectionController.getAllCollections().stream()
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
		collectionList.setEditable(true);
		collectionList.setCellFactory(TextFieldListCell.forListView());
	}

	private void setUpButtons() {
		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");
		Button newCollectionButton = new Button("New Collection");
		saveButton.setOnAction(event -> saveRequest());
		cancelButton.setOnAction(event -> stage.close());
		newCollectionButton.setOnAction(event -> createNewCollection());
	}

	private void saveRequest() {
		collectionController.addRequestToCollection(
				collectionList.getItems().get(collectionList.getSelectionModel().getSelectedIndex()),
				requestController.getActiveRequest());
		stage.close();
	}

	private void createNewCollection() {
		// Add an empty string to the ListView for the user to edit
		collectionList.getItems().add("");
		collectionList.getSelectionModel().selectLast(); // Focus the new entry for immediate editing
		collectionList.edit(collectionList.getItems().size() - 1); // Explicitly start editing the newly added item
	}

	public record CollectionInfo(String name, String id){
		@Override
		public String toString() {
			return name;
		}
	}

}
