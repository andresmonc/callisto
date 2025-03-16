package com.jmoncayo.callisto.collection;

import com.jmoncayo.callisto.storage.Loadable;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService implements Loadable<Collection> {

    @Getter
    private List<Collection> collections;

    public CollectionService(List<Collection> collections) {
        this.collections = collections;
    }

    @Override
    public void load(List<Collection> data) {
        this.collections = data;
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("test!!!!!!!");
    }
}
