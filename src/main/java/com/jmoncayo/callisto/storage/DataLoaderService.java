package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.CollectionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataLoaderService {

    private final FileStorageService fileStorageService;
    private final CollectionService collectionService;

    @Autowired
    public DataLoaderService(FileStorageService fileStorageService, CollectionService collectionService) {
        this.fileStorageService = fileStorageService;
        this.collectionService = collectionService;
    }

    @PostConstruct
    public void loadDataToServices() {
        try {
            Storage data = fileStorageService.loadFromFile(Storage.class, "fileName");
            collectionService.load(data.getUnSavedCollections());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
