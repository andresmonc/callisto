package com.jmoncayo.callisto.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs(); // Creates the directories if they don't exist
		}

		// Check if the file exists, and create it if it doesn't
		if (!file.exists()) {
			file.createNewFile(); // Creates the file if it does not exist
		}

		// Now, write the object to the file
		mapper.writeValue(file, object);
	}

	public <T> T loadFromFile(Class<T> type, String filename) throws IOException {
		File file = getFilePath(filename).toFile();
		return mapper.readValue(file, type);
	}
}
