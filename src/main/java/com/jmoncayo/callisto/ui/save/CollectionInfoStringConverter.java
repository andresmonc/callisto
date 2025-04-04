package com.jmoncayo.callisto.ui.save;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.ui.controllers.CollectionController;
import javafx.util.StringConverter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CollectionInfoStringConverter extends StringConverter<CollectionInfo> {

	private final CollectionController collectionController;

	@Setter
	private boolean isSubfolder;

	@Setter
	private String parentCollectionId;

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
		Collection collection;
		if (isSubfolder) {
			log.info("created subfolder");
			collection = collectionController.addSubfolderToCollection(parentCollectionId, name);
		} else {
			log.info("created collection");
			collection = collectionController.addCollection(name);
		}
		return new CollectionInfo(name, collection.getId());
	}
}
