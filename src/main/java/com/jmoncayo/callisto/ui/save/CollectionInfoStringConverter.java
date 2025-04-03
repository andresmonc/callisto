package com.jmoncayo.callisto.ui.save;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectionInfoStringConverter extends StringConverter<CollectionInfo> {

    private final CollectionController collectionController;

    @Autowired
    public CollectionInfoStringConverter(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public String toString(CollectionInfo collection) {
        return collection == null ? "" : collection.getName();
    }

    @Override
    public CollectionInfo fromString(String name) {
        Collection collection = collectionController.addCollection(name);
        return new CollectionInfo(name, collection.getId());
    }
}