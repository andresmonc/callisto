package com.jmoncayo.callisto.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final ObjectMapper mapper;
    private static final String STORAGE_DIR = "data";

    @Autowired
    public FileStorageService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private Path getFilePath(String filename) {
        return Paths.get(STORAGE_DIR, filename);
    }

    public void saveToFile(Object object, String filename) throws IOException {
        File file = getFilePath(filename).toFile();
        mapper.writeValue(file, object);
    }

    public <T> T loadFromFile(Class<T> type, String filename) throws IOException {
        File file = getFilePath(filename).toFile();
        return mapper.readValue(file, type);
    }
}
