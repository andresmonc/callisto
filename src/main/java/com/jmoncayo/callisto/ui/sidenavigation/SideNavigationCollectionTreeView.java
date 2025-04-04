package com.jmoncayo.callisto.ui.sidenavigation;
import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import java.util.List;

import com.jmoncayo.callisto.ui.requestview.RequestsViewer;
import javafx.scene.control.*;
import javafx.scene.input.*;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static org.kordamp.ikonli.fontawesome5.FontAwesomeRegular.FOLDER;
import static org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.CHECK;

@Component
@Log4j2
public class SideNavigationCollectionTreeView extends TreeView<String> {

	private final CollectionController collectionController;
	private final ApplicationEventPublisher eventPublisher;

	public SideNavigationCollectionTreeView(CollectionController collectionController, ApplicationEventPublisher eventPublisher) {
		this.collectionController = collectionController;
		this.eventPublisher = eventPublisher;
		TreeItem<String> root = new TreeItem<>("Collections");
		root.setExpanded(true); // Start with root expanded

		List<Collection> collections = collectionController.getAllCollections();
		for (Collection collection : collections) {
			TreeItem<String> collectionItem = createCollectionTreeItem(collection);
			root.getChildren().add(collectionItem);
		}

		this.setRoot(root);
		this.setShowRoot(false);
		this.setCellFactory(param -> createCustomTreeCell());
		enableDragAndDrop();
	}

	private TreeItem<String> createCollectionTreeItem(Collection collection) {
		TreeItem<String> collectionItem = new TreeItem<>(collection.getName());
		for (Collection subfolder : collection.getSubfolders()) {
			TreeItem<String> subfolderItem = createCollectionTreeItem(subfolder);
			collectionItem.getChildren().add(subfolderItem);
		}
		for (ApiRequest request : collection.getRequests()) {
			TreeItem<String> requestItem = new TreeItem<>(request.getName());
			collectionItem.getChildren().add(requestItem);
		}
		return collectionItem;
	}

	private TreeCell<String> createCustomTreeCell() {
		return new TreeCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item);
					// Set icon depending on the type of item (e.g., folder or request)
					if (getTreeItem().getChildren().isEmpty()) {
						// This is a request item, so set a request icon using Ikonli FontAwesome5
						FontIcon requestIcon = new FontIcon(CHECK);
						requestIcon.setIconSize(16);
						requestIcon.setIconColor(javafx.scene.paint.Color.GREEN);
						setGraphic(requestIcon);
//						setGraphic(new ImageView(new Image("request-icon.png")));
					} else {
						// This is a folder/subfolder item, set a folder icon using Ikonli FontAwesome5
						FontIcon folderIcon = new FontIcon(FOLDER);
						folderIcon.setIconSize(16);
						folderIcon.setIconColor(javafx.scene.paint.Color.BLUE);
						setGraphic(folderIcon);
//						setGraphic(new ImageView(new Image("request-icon.png")));
					}
					// Handle mouse click event for both requests and folders
					setOnMouseClicked(this::handleItemClick);
				}
			}

			// Handling clicks for collections and requests
			private void handleItemClick(MouseEvent event) {
				TreeItem<String> selectedItem = getTreeItem();
				if (selectedItem != null) {
					if (selectedItem.getChildren().isEmpty()) {
						// Handle request click (no children = request)
						log.info("Request clicked: " + selectedItem.getValue());
						// Trigger your specific logic for requests here

					} else {
						// Handle folder click (has children = folder/subfolder)
						log.info("Collection clicked: " + selectedItem.getValue());
						// Trigger your specific logic for collections here
					}
				}
			}
		};
	}

	// Drag-and-drop logic to move requests between subfolders
	public void enableDragAndDrop() {
		this.setOnDragDetected(event -> {
			TreeItem<String> selectedItem = getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				// Start dragging request ID when a request item is selected
				String requestId = selectedItem.getValue();
				Dragboard db = startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putString(requestId);
				db.setContent(content);
				event.consume();
			}
		});

		this.setOnDragOver(event -> {
			if (event.getGestureSource() != this && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		this.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				String requestId = db.getString();
				log.info(requestId);
				TreeItem<String> targetItem = getSelectionModel().getSelectedItem();
				if (targetItem != null && !targetItem.getChildren().isEmpty()) {
					// Move request to the new folder/subfolder
					String targetFolderId = targetItem.getValue();  // Handle moving the request to this folder
					// Implement your logic to move the request to the new folder
					success = true;
				}
			}
			event.setDropCompleted(success);
			event.consume();
		});
	}
}
