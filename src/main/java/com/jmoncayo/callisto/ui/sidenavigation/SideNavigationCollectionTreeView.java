package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationCollectionTreeView extends TreeView<CollectionTreeNode> {

	private final ApplicationEventPublisher eventPublisher;

	public SideNavigationCollectionTreeView(
			CollectionController collectionController, ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
		TreeItem<CollectionTreeNode> root = new TreeItem<>(new CollectionTreeNode("Collections", null, null));
		root.setExpanded(true);

		for (Collection collection : collectionController.getAllCollections()) {
			TreeItem<CollectionTreeNode> collectionItem = createCollectionTreeItem(collection);
			root.getChildren().add(collectionItem);
		}
		this.setRoot(root);
		this.setShowRoot(false);
		this.setCellFactory(tv -> new TreeCell<>() {
			@Override
			protected void updateItem(CollectionTreeNode item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item.getDisplayName());
					if (item.isRequest()) {
						// FA icon or fallback to image
						setGraphic(new FontIcon("fas-file-alt"));
						// setGraphic(new ImageView(new Image("request-icon.png"))); // ← image fallback
					} else {
						setGraphic(new FontIcon("fas-folder"));
						// setGraphic(new ImageView(new Image("folder-icon.png"))); // ← image fallback
					}
				}
			}
		});

		this.setOnMouseClicked(event -> {
			TreeItem<CollectionTreeNode> selected = this.getSelectionModel().getSelectedItem();
			if (selected != null) {
				CollectionTreeNode node = selected.getValue();
				if (node.isRequest()) {
					System.out.println("Clicked request: " + node.getRequestId());
					eventPublisher.publishEvent(new LaunchRequest(this, node.getRequestId()));
					// TODO: Open or trigger request
				} else if (node.getCollectionId() != null) {
					System.out.println("Clicked collection: " + node.getCollectionId());
					// TODO: Expand collection or show info
				}
			}
		});
	}

	private TreeItem<CollectionTreeNode> createCollectionTreeItem(Collection collection) {
		TreeItem<CollectionTreeNode> collectionItem =
				new TreeItem<>(new CollectionTreeNode(collection.getName(), collection.getId(), null));
		collectionItem.setExpanded(true);
		for (Collection subfolder : collection.getSubfolders()) {
			TreeItem<CollectionTreeNode> subfolderItem = createCollectionTreeItem(subfolder);
			collectionItem.getChildren().add(subfolderItem);
		}
		for (ApiRequest request : collection.getRequests()) {
			TreeItem<CollectionTreeNode> requestItem =
					new TreeItem<>(new CollectionTreeNode(request.getName(), null, request.getId()));
			collectionItem.getChildren().add(requestItem);
		}
		return collectionItem;
	}
}
