package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.preferences.Preferences;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
public class Storage {
    List<Collection> savedCollections;
    List<Collection> unSavedCollections;
    Preferences preferences;

    public Storage() {
    }

    public Storage(List<Collection> savedCollections, List<Collection> unSavedCollections, Preferences preferences) {
        this.savedCollections = savedCollections;
        this.unSavedCollections = unSavedCollections;
        this.preferences = preferences;
    }
}
