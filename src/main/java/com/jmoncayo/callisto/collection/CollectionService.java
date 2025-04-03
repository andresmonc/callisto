package com.jmoncayo.callisto.collection;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.storage.Loadable;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CollectionService implements Loadable<Collection> {

	private final CollectionRepository collectionRepository;

	public CollectionService(CollectionRepository collectionRepository) {
		this.collectionRepository = collectionRepository;
	}

	@Override
	public void load(List<Collection> data) {
		// Load collections into repository on startup
		collectionRepository.saveAll(data);
		log.info("Loaded collections into repository: " + data.size());
	}

	public Collection createCollection(String name) {
		Collection collection = Collection.builder().name(name).build();
		collectionRepository.save(collection);
		log.info("Created collection: " + name);
		return collection;
	}

	public void renameCollection(String collectionId, String newName) {
		Collection collection = collectionRepository.findById(collectionId)
				.orElseThrow(() -> new RuntimeException("Collection not found")).toBuilder().name(newName).build();
		collectionRepository.save(collection);
		log.info("Renamed collection " + collectionId + " to " + newName);
	}

	public void deleteCollection(String collectionId) {
		Collection collection = collectionRepository.findById(collectionId)
				.orElseThrow(() -> new RuntimeException("Collection not found"));
		collectionRepository.delete(collection);
		log.info("Deleted collection: " + collectionId);
	}

	public Subfolder addSubfolder(String collectionId, String subfolderName) {
		Collection collection = collectionRepository.findById(collectionId)
				.orElseThrow(() -> new RuntimeException("Collection not found"));
		Subfolder subfolder = Subfolder.builder().name(subfolderName).build();
		collection.getSubfolders().add(subfolder);
		collectionRepository.save(collection);
		log.info("Added subfolder " + subfolderName + " to collection " + collectionId);
		return subfolder;
	}

	public List<Subfolder> getSubfolders(String parentId) {
		Collection collection = collectionRepository.findById(parentId)
				.orElseThrow(() -> new RuntimeException("Collection not found"));
		return collection.getSubfolders();
	}

	public Subfolder addSubfolderToSubfolder(String parentSubfolderId, String subfolderName) {
		Subfolder parentSubfolder = findSubfolderById(parentSubfolderId);
		Subfolder subfolder = Subfolder.builder().name(subfolderName).build();
		parentSubfolder.getSubfolders().add(subfolder);
		log.info("Added subfolder " + subfolderName + " to subfolder " + parentSubfolderId);
		return subfolder;
	}

	public void deleteSubfolder(String subfolderId) {
		Collection collection = collectionRepository.findBySubfolderId(subfolderId)
				.orElseThrow(() -> new RuntimeException("Subfolder not found"));

		Subfolder subfolder = findSubfolderById(subfolderId);
		collection.getSubfolders().remove(subfolder);
		collectionRepository.save(collection);
		log.info("Deleted subfolder: " + subfolderId);
	}

	public void addRequestToCollection(String collectionId, ApiRequest request) {
		Collection collection = collectionRepository.findById(collectionId)
				.orElseThrow(() -> new RuntimeException("Collection not found"));

		collection.getRequests().add(request);
		collectionRepository.save(collection);
		log.info("Added request to collection " + collectionId);
	}

	public void addRequestToSubfolder(String subfolderId, ApiRequest request) {
		Subfolder subfolder = findSubfolderById(subfolderId);

		subfolder.getRequests().add(request);
		log.info("Added request to subfolder " + subfolderId);
	}

	private Subfolder findSubfolderById(String subfolderId) {
		for (Collection collection : collectionRepository.findAll()) {
			for (Subfolder subfolder : collection.getSubfolders()) {
				if (subfolder.getId().equals(subfolderId)) {
					return subfolder;
				}
			}
		}
		throw new RuntimeException("Subfolder not found");
	}

	public List<Collection> getAllCollections() {
		return collectionRepository.findAll();
	}
}


