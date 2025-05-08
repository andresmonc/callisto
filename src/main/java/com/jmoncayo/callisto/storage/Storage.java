package com.jmoncayo.callisto.storage;

import com.jmoncayo.callisto.collection.Collection;
import com.jmoncayo.callisto.preferences.Preferences;
import com.jmoncayo.callisto.requests.ApiRequest;
import java.util.List;
import lombok.Data;

@Data
public class Storage {
	List<Collection> collections;
	List<ApiRequest> requests;
	Preferences preferences;

	public Storage() {}

	public Storage(
			List<Collection> savedCollections,
			List<Collection> collections,
			Preferences preferences,
			List<ApiRequest> requests) {
		this.collections = collections;
		this.preferences = preferences;
		this.requests = requests;
	}
}
