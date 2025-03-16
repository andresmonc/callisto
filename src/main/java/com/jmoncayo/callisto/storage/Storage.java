package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.preferences.Preferences;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Storage {
    List<Collection> savedCollections;
    List<Collection> unSavedCollections;
    Preferences preferences;
}
