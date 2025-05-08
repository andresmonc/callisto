package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.CollectionService;
import com.jmoncayo.callisto.requests.ApiRequestService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DataLoaderService {

	private final FileStorageService fileStorageService;
	private final CollectionService collectionService;
	private final ApiRequestService apiRequestService;
	private final String saveName;

	@Autowired
	public DataLoaderService(
			FileStorageService fileStorageService,
			CollectionService collectionService,
			ApiRequestService apiRequestService,
			@Value("${callisto.save.name}") String saveName) {
		this.fileStorageService = fileStorageService;
		this.collectionService = collectionService;
		this.apiRequestService = apiRequestService;
		this.saveName = saveName;
	}

	@PostConstruct
	public void loadDataToServices() throws IOException {
		try {
			Storage data = fileStorageService.loadFromFile(Storage.class, saveName);
			collectionService.load(data.getCollections());
			apiRequestService.load(data.getRequests());
		} catch (IOException e) {
			fileStorageService.saveToFile(new Storage(), saveName);
			log.error(e);
		}
	}

	@PreDestroy
	public void saveDataFromServices() {
		try {
			var storage = new Storage();
			storage.setCollections(collectionService.getAllCollections());
			storage.setRequests(apiRequestService.getAllRequests());
			fileStorageService.saveToFile(storage, saveName);
		} catch (IOException e) {
			log.error(e);
		}
	}
}
