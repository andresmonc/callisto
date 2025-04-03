package com.jmoncayo.callisto.ui.save;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import com.jmoncayo.callisto.ui.controllers.RequestController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class SaveRequestDialog {
	private final ListView<CollectionInfo> collectionList;
	private final TextField newCollectionField;
	private final CollectionController collectionController;
	private final RequestController requestController;
	private final CollectionInfoStringConverter converter;
	private Stage stage;
	private CollectionInfo selectedCollection;

	// Inject the service to fetch collections
	public SaveRequestDialog(CollectionController collectionController, RequestController requestController,CollectionInfoStringConverter converter) {
		this.collectionController = collectionController;
		this.requestController = requestController;
		this.converter = converter;
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
		return new VBox(
				10,
				new Label("Select a collection or create a new one:"),
				collectionList,
				newCollectionButton,
				saveButton,
				cancelButton);
	}

	private void setUpCollectionList() {
		List<CollectionInfo> collections = collectionController.getAllCollections().stream()
				.map(c -> new CollectionInfo(c.getName(), c.getId()))
				.toList();
		collectionList.getItems().setAll(collections);

		collectionList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Handle item click (when selection changes to a new item)
				log.info("Selected: " + newValue.getName());
				log.info("Selected: " + newValue.getId());
				selectedCollection = newValue;
				// You can trigger any actions you need here based on the selected item
			}
		});

		collectionList.setOnEditCommit(event -> {
			String newValue = event.getNewValue().getName();
			if (newValue == null || newValue.isEmpty()) {
				collectionList.getItems().remove(event.getIndex());
				return;
			}
			collectionList.getItems().set(event.getIndex(), event.getNewValue());
		});
		collectionList.setEditable(true);
		collectionList.setCellFactory(listView -> new TextFieldListCell<>(converter));
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
				collectionList.getItems().get(collectionList.getSelectionModel().getSelectedIndex()).getId(),
				requestController.getActiveRequest());
		stage.close();
	}

	private void createNewCollection() {
		collectionList.getItems().add(new CollectionInfo("", ""));
		collectionList.getSelectionModel().selectLast();
		collectionList.edit(collectionList.getItems().size() - 1);
	}

}
