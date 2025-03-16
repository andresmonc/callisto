package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.Collection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Storage {
    private List<Collection> savedCollections;
    private List<Collection> unSavedCollections;
}
