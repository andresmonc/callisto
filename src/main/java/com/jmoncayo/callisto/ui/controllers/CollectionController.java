package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.collection.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    public List<Collection> getCollections() {
        return collectionService.getCollections();
    }
}
