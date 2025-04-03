package com.jmoncayo.callisto.ui.save;

import com.jmoncayo.callisto.collection.Subfolder;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import com.jmoncayo.callisto.ui.controllers.RequestController;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SaveRequestDialog {
	private final TreeView<CollectionInfo> collectionTree;
	private final TextField newCollectionField;
	private final CollectionController collectionController;
	private final RequestController requestController;
	private final CollectionInfoStringConverter converter;
	private Stage stage;
	private CollectionInfo selectedCollection;
	private Button saveButton;
	private Button newCollectionButton;
	private Button cancelButton;
	private Button subFolderButton;

	// Inject the service to fetch collections
	public SaveRequestDialog(
			CollectionController collectionController,
			RequestController requestController,
			CollectionInfoStringConverter converter) {
		this.collectionController = collectionController;
		this.requestController = requestController;
		this.converter = converter;
		collectionTree = new TreeView<>();
		newCollectionField = new TextField();
		newCollectionField.setPromptText("New collection/subfolder name");
	}

	public void open(Stage owner) {
		stage = createStage(owner);
		setUpButtons();
		VBox layout = createLayout();
		setUpCollectionTree();
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
		return new VBox(
				10,
				new Label("Select a collection or create a new one:"),
				collectionTree,
				newCollectionButton,
				saveButton,
				cancelButton,
				subFolderButton);
	}

	private void setUpCollectionTree() {
		// Get all collections
		List<CollectionInfo> collections = collectionController.getAllCollections().stream()
				.map(c -> new CollectionInfo(c.getName(), c.getId()))
				.toList();

		// Build TreeItems for collections
		TreeItem<CollectionInfo> root = new TreeItem<>();

		// Create TreeItems for collections and subfolders
		for (CollectionInfo collection : collections) {
			TreeItem<CollectionInfo> collectionItem = new TreeItem<>(collection);
			root.getChildren().add(collectionItem);

			// Add subfolders as children if available
			List<Subfolder> subfolders = collectionController.getSubfolders(collection.getId());
			for (Subfolder subfolder : subfolders) {
				TreeItem<CollectionInfo> subfolderItem =
						new TreeItem<>(new CollectionInfo(subfolder.getName(), subfolder.getId()));
				collectionItem.getChildren().add(subfolderItem);
			}
		}

		collectionTree.setRoot(root);
		collectionTree.setShowRoot(false);
		collectionTree.setEditable(true);
		collectionTree.setCellFactory(param -> new TextFieldTreeCell<>(converter) {});

		collectionTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue.getValue() != null) {
				// Handle item click
				log.info("Selected: " + newValue.getValue().getName());
				log.info("Selected: " + newValue.getValue().getId());
				selectedCollection = newValue.getValue();
			}
		});
	}

	private void setUpButtons() {
		saveButton = new Button("Save");
		cancelButton = new Button("Cancel");
		newCollectionButton = new Button("New Collection");
		subFolderButton = new Button("New Subfolder");

		saveButton.setOnAction(event -> saveRequest());
		cancelButton.setOnAction(event -> stage.close());
		newCollectionButton.setOnAction(event -> createNewCollection());
		subFolderButton.setOnAction(event -> createNewSubFolder());
	}

	private void saveRequest() {
		collectionController.addRequestToCollection(selectedCollection.getId(), requestController.getActiveRequest());
		stage.close();
	}

	private void createNewCollection() {
		// Create a new collection or subfolder
		TreeItem<CollectionInfo> newItem = new TreeItem<>(new CollectionInfo("", ""));
		collectionTree.getRoot().getChildren().add(newItem);
		collectionTree.getSelectionModel().select(newItem);
		collectionTree.edit(newItem);
	}

	private void createNewSubFolder() {
		TreeItem<CollectionInfo> newItem = new TreeItem<>(new CollectionInfo("", ""));
		collectionTree.getSelectionModel().getSelectedItem().getChildren().add(newItem);
		collectionTree.getSelectionModel().select(newItem);
		collectionTree.edit(newItem);
	}
}
