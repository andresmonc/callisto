package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.CollectionService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
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
            fileStorageService.saveToFile(Storage.builder().build(), saveName);
        }
    }

    @PreDestroy
    public void saveDataFromServices() throws IOException {
        try {
            fileStorageService.saveToFile(Storage.builder().savedCollections(collectionService.getCollections()).build(), saveName);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
