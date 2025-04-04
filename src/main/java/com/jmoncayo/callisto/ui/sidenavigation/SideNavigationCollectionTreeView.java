package com.jmoncayo.callisto.ui.sidenavigation;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import com.jmoncayo.callisto.ui.events.LaunchRequestEvent;
import com.jmoncayo.callisto.ui.events.NewCollectionEvent;
import com.jmoncayo.callisto.ui.events.RequestRenamedEvent;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SideNavigationCollectionTreeView extends TreeView<CollectionTreeNode> {

	private final CollectionController collectionController;
	private final ApplicationEventPublisher eventPublisher;

	public SideNavigationCollectionTreeView(
			CollectionController collectionController, ApplicationEventPublisher eventPublisher) {
		this.collectionController = collectionController;
		this.eventPublisher = eventPublisher;
		initialize();
	}

	private void initialize() {
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
					log.info("Clicked request: " + node.getRequestId());
					eventPublisher.publishEvent(new LaunchRequestEvent(this, node.getRequestId()));
					// TODO: Open or trigger request
				} else if (node.getCollectionId() != null) {
					log.info("Clicked collection: " + node.getCollectionId());
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

	@EventListener(RequestRenamedEvent.class)
	public void onApplicationEvent(RequestRenamedEvent requestRenamedEvent) {
		String requestId = requestRenamedEvent.getRequestId();
		String newName = requestRenamedEvent.getNewName();

		TreeItem<CollectionTreeNode> root = getRoot();
		TreeItem<CollectionTreeNode> match = findRequestTreeItem(root, requestId);
		if (match != null) {
			CollectionTreeNode updatedNode = new CollectionTreeNode(newName, null, requestId);
			match.setValue(updatedNode);
		}
	}

	@EventListener(NewCollectionEvent.class)
	public void onApplicationEvent(NewCollectionEvent requestRenamedEvent) {
		createCollectionTreeItem(requestRenamedEvent.getCollection());
		log.info("new collection added to sidenav");
		// todo: fix this
	}

	private TreeItem<CollectionTreeNode> findRequestTreeItem(TreeItem<CollectionTreeNode> node, String requestId) {
		if (node == null || node.getValue() == null) return null;
		CollectionTreeNode value = node.getValue();
		if (value.isRequest() && requestId.equals(value.getRequestId())) {
			return node;
		}
		for (TreeItem<CollectionTreeNode> child : node.getChildren()) {
			TreeItem<CollectionTreeNode> result = findRequestTreeItem(child, requestId);
			if (result != null) return result;
		}
		return null;
	}
}
