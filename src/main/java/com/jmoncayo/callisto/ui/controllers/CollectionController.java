package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.collection.CollectionService;
import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.ui.events.NewCollectionEvent;
import com.jmoncayo.callisto.ui.events.RequestAddedToCollectionEvent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CollectionController {

	private final CollectionService collectionService;
	private final ApplicationEventPublisher eventPublisher;

	@Autowired
	public CollectionController(CollectionService collectionService, ApplicationEventPublisher eventPublisher) {
		this.collectionService = collectionService;
		this.eventPublisher = eventPublisher;
	}

	public List<Collection> getAllCollections() {
		return collectionService.getAllCollections();
	}

	public Collection addCollection(String name) {
		Collection collection = collectionService.createCollection(name);
		eventPublisher.publishEvent(new NewCollectionEvent(this, collection));
		return collection;
	}

	public Collection createCollection(String name) {
		return collectionService.createCollection(name);
	}

	public void renameCollection(String collectionId, String newName) {
		collectionService.renameCollection(collectionId, newName);
	}

	public void deleteCollection(String collectionId) {
		collectionService.deleteCollection(collectionId);
	}

	public Collection addSubfolderToCollection(String collectionId, String subfolderName) {
		return collectionService.addSubfolder(collectionId, subfolderName);
	}

	public Collection addSubfolderToSubfolder(String parentSubfolderId, String subfolderName) {
		return collectionService.addSubfolderToSubfolder(parentSubfolderId, subfolderName);
	}

	public void deleteSubfolder(String subfolderId) {
		collectionService.deleteSubfolder(subfolderId);
	}

	public void addRequestToCollection(String collectionId, ApiRequest request) {
		collectionService.addRequestToCollection(collectionId, request);
		eventPublisher.publishEvent(new RequestAddedToCollectionEvent(this, request, collectionId));
	}

	public void addRequestToSubfolder(String subfolderId, ApiRequest request) {
		collectionService.addRequestToSubfolder(subfolderId, request);
	}

	public List<Collection> getSubfolders(String parentId) {
		return collectionService.getSubfolders(parentId);
	}
}
