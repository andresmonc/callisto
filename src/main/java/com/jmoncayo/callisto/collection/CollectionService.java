package com.jmoncayo.callisto.collection;

import com.jmoncayo.callisto.storage.Loadable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class CollectionService implements Loadable<Collection> {

	@Getter
	private List<Collection> collections = new ArrayList<>();

	@Override
	public void load(List<Collection> data) {
		this.collections = Objects.requireNonNullElseGet(data, ArrayList::new);
	}

	public Collection createCollection() {
		Collection collection = Collection.builder().name("New Collection").build();
		collections.add(collection);
		return collection;
	}
}
