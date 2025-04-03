package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.collection.CollectionService;
import com.jmoncayo.callisto.requests.ApiRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectionController {

	private final CollectionService collectionService;

	@Autowired
	public CollectionController(CollectionService collectionService) {
		this.collectionService = collectionService;
	}

	public List<Collection> getAllCollections() {
		return collectionService.getAllCollections();
	}

	public Collection addCollection(String name) {
		return collectionService.createCollection(name);
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
	}

	public void addRequestToSubfolder(String subfolderId, ApiRequest request) {
		collectionService.addRequestToSubfolder(subfolderId, request);
	}

	public List<Collection> getSubfolders(String parentId) {
		return collectionService.getSubfolders(parentId);
	}
}
