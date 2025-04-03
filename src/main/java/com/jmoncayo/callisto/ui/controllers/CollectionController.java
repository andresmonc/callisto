package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.collection.CollectionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectionController {

	private final CollectionService collectionService;
	private final RequestController requestController;

	@Autowired
	public CollectionController(CollectionService collectionService, RequestController requestController) {
		this.collectionService = collectionService;
		this.requestController = requestController;
	}

	public List<Collection> getCollections() {
		return collectionService.getCollections();
	}

	public Collection addCollection(String name) {
		return collectionService.createCollection(name);
	}

	public void addRequestToCollection(String collectionName) {
		collectionService.addRequestToCollection(collectionName);
	}
}
