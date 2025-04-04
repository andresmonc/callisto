package com.jmoncayo.callisto.collection;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CollectionRepository {
	private final Map<String, Collection> collections = new HashMap<>();
	private final Map<String, String> parentMap = new HashMap<>(); // Maps child ID → parent ID

	public void save(Collection collection) {
		saveRecursive(collection, null);
	}

	private void saveRecursive(Collection collection, String parentId) {
		collections.put(collection.getId(), collection);
		if (parentId != null) {
			parentMap.put(collection.getId(), parentId);
		}

		for (Collection subfolder : collection.getSubfolders()) {
			saveRecursive(subfolder, collection.getId());
		}
	}

	public Optional<Collection> findById(String collectionId) {
		return Optional.ofNullable(collections.get(collectionId));
	}

	public Optional<Collection> findParentById(String collectionId) {
		String parentId = parentMap.get(collectionId);
		return parentId != null ? findById(parentId) : Optional.empty();
	}

	public void delete(Collection collection) {
		deleteRecursive(collection);
	}

	private void deleteRecursive(Collection collection) {
		collections.remove(collection.getId());
		parentMap.remove(collection.getId());

		for (Collection subfolder : collection.getSubfolders()) {
			deleteRecursive(subfolder);
		}
	}

	public List<Collection> findAll() {
		return collections.values().stream()
				.filter(c -> !parentMap.containsKey(c.getId())) // Only return top-level collections
				.toList();
	}

	public void saveAll(List<Collection> collections) {
		for (Collection collection : collections) {
			saveRecursive(collection, null);
		}
		System.out.println("done!");
	}
}
