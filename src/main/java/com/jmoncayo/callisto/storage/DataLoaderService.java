package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.CollectionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataLoaderService {

    private final FileStorageService fileStorageService;
    private final CollectionService collectionService;
    private final String saveName;

    @Autowired
    public DataLoaderService(FileStorageService fileStorageService, CollectionService collectionService,
                             @Value("${callisto.save.name}") String saveName) {
        this.fileStorageService = fileStorageService;
        this.collectionService = collectionService;
        this.saveName = saveName;
    }

    @PostConstruct
    public void loadDataToServices() throws IOException {
        try {
            Storage data = fileStorageService.loadFromFile(Storage.class, saveName);
            collectionService.load(data.getUnSavedCollections());
        } catch (IOException e) {
            fileStorageService.saveToFile(new Storage(), saveName);
        }
    }
}
