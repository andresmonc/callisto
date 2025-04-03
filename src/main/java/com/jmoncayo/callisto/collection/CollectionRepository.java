package com.jmoncayo.callisto.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CollectionRepository {

	private final Map<String, Collection> collections = new HashMap<>();

	public void save(Collection collection) {
		collections.put(collection.getId(), collection);
	}

	public Optional<Collection> findById(String collectionId) {
		return Optional.ofNullable(collections.get(collectionId));
	}

	public void delete(Collection collection) {
		collections.remove(collection.getId());
	}

	public void saveAll(List<Collection> collections) {
		for (Collection collection : collections) {
			save(collection);
		}
	}

	public List<Collection> findAll() {
		return new ArrayList<>(collections.values());
	}

	public Optional<Collection> findBySubfolderId(String subfolderId) {
		for (Collection collection : collections.values()) {
			for (Subfolder subfolder : collection.getSubfolders()) {
				if (subfolder.getId().equals(subfolderId)) {
					return Optional.of(collection);
				}
			}
		}
		return Optional.empty();
	}
}
