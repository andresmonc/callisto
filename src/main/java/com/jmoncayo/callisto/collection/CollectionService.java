package com.jmoncayo.callisto.collection;

import com.jmoncayo.callisto.storage.Loadable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CollectionService implements Loadable<Collection> {

	@Getter
	private List<Collection> collections = new ArrayList<>();

	@Override
	public void load(List<Collection> data) {
		this.collections = Objects.requireNonNullElseGet(data, ArrayList::new);
	}

	public Collection createCollection(String name) {
		name = name == null ? "Untitled Collection" : name;
		Collection collection = Collection.builder().name(name).build();
		collections.add(collection);
		log.info("Collection created: " + collection.getId());
		return collection;
	}
}
