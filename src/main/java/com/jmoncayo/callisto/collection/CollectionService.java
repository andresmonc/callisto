package com.jmoncayo.callisto.collection;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.ApiRequestService;
import com.jmoncayo.callisto.storage.Loadable;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CollectionService implements Loadable<Collection> {

	private final CollectionRepository collectionRepository;
	private final ApiRequestService apiRequestService;

	public CollectionService(CollectionRepository collectionRepository, ApiRequestService apiRequestService) {
		this.collectionRepository = collectionRepository;
		this.apiRequestService = apiRequestService;
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
		Collection collection =
				collectionRepository
						.findById(collectionId)
						.orElseThrow(() -> new RuntimeException("Collection not found"))
						.toBuilder()
						.name(newName)
						.build();
		collectionRepository.save(collection);
		log.info("Renamed collection " + collectionId + " to " + newName);
	}

	public void deleteCollection(String collectionId) {
		Collection collection = collectionRepository
				.findById(collectionId)
				.orElseThrow(() -> new RuntimeException("Collection not found"));
		collectionRepository.delete(collection);
		log.info("Deleted collection: " + collectionId);
	}

	public Collection addSubfolder(String collectionId, String subfolderName) {
		Collection collection = collectionRepository
				.findById(collectionId)
				.orElseThrow(() -> new RuntimeException("Collection not found"));
		Collection subfolder = Collection.builder().name(subfolderName).build();
		collection.getSubfolders().add(subfolder);
		collectionRepository.save(collection);
		log.info("Added subfolder " + subfolderName + " to collection " + collectionId);
		return subfolder;
	}

	public List<Collection> getSubfolders(String parentId) {
		Collection collection =
				collectionRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Collection not found"));
		return collection.getSubfolders();
	}

	public Collection addSubfolderToSubfolder(String parentSubfolderId, String subfolderName) {
		Collection parentSubfolder = findSubfolderById(parentSubfolderId);
		Collection subfolder = Collection.builder().name(subfolderName).build();
		parentSubfolder.getSubfolders().add(subfolder);
		log.info("Added subfolder " + subfolderName + " to subfolder " + parentSubfolderId);
		return subfolder;
	}

	public void deleteSubfolder(String subfolderId) {
		Collection parentCollection = collectionRepository
				.findParentById(subfolderId)
				.orElseThrow(() -> new RuntimeException("Parent collection not found for subfolder: " + subfolderId));
		Collection subfolderToDelete = parentCollection.getSubfolders().stream()
				.filter(subfolder -> subfolder.getId().equals(subfolderId))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Subfolder not found in parent"));
		parentCollection.getSubfolders().remove(subfolderToDelete);
		collectionRepository.save(parentCollection);
		collectionRepository.delete(subfolderToDelete);
		log.info("Deleted subfolder: " + subfolderId);
	}

	public void addRequestToCollection(String collectionId, ApiRequest request) {
		Collection collection = collectionRepository
				.findById(collectionId)
				.orElseThrow(() -> new RuntimeException("Collection not found"));

		apiRequestService.setCollectionIdOnRequest(request.getId(), collection.getId());
		collectionRepository.save(collection);
		log.info("Added request " + request.getName() + " to collection " + collection.getName());
	}

	public void addRequestToSubfolder(String subfolderId, ApiRequest request) {
		Collection subfolder = collectionRepository
				.findById(subfolderId)
				.orElseThrow(() -> new RuntimeException("Subfolder not found: " + subfolderId));
		apiRequestService.setCollectionIdOnRequest(request.getId(), subfolder.getId());
		collectionRepository.save(subfolder);
		log.info("Added request to subfolder " + subfolderId);
	}

	private Collection findSubfolderById(String subfolderId) {
		for (Collection collection : collectionRepository.findAll()) {
			for (Collection subfolder : collection.getSubfolders()) {
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
