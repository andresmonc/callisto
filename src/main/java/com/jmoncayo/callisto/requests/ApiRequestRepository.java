package com.jmoncayo.callisto.requests;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class ApiRequestRepository {

	private final Map<String, ApiRequest> requests = new HashMap<>();

	public ApiRequest getApiRequest(String requestUUID) {
		ApiRequest request = requests.get(requestUUID);
		return getSavedOrUnsavedRequest(request);
	}

	public void update(ApiRequest request) {
		if(!requests.containsKey(request.getId())){
			requests.put(request.getId(), request);
			return;
		}
		ApiRequest newRequest = requests.get(request.getId()).toBuilder().unsavedChanges(request).build();
		requests.put(request.getId(), newRequest);
	}

	public List<ApiRequest> getActiveRequests() {
		return requests.values().stream().filter(ApiRequest::isActive).map(this::getSavedOrUnsavedRequest).toList();
	}

	public List<ApiRequest> getAllRequests() {
		return requests.values().stream().map(this::getSavedOrUnsavedRequest).toList();
	}

	public void putAll(List<ApiRequest> requests) {
		if (requests == null) {
			return;
		}
		this.requests.putAll(requests.stream().collect(Collectors.toMap(ApiRequest::getId, o -> o)));
	}

	public void delete(ApiRequest request) {
		log.info("Deleting request: " + request.getId());
		requests.remove(request.getId());
	}

	public ApiRequest getSavedOrUnsavedRequest(ApiRequest request) {
		ApiRequest unsavedChanges = request.getUnsavedChanges();
		if (unsavedChanges != null) {
			return unsavedChanges;
		}
		return request;
	}

	public void save(ApiRequest request) {
		if (request.getUnsavedChanges() != null) {
			request = request.toBuilder().hasParent(true).unsavedChanges(null).build();
		}
		requests.put(request.getId(), request);
		log.info("Saving request: " + request.getId());
	}

	public List<ApiRequest> getRequestsForCollectionID(String id) {
		return requests.values().stream()
				.filter(request -> request.getCollectionId() != null && request.getCollectionId().equals(id))
				.map(this::getSavedOrUnsavedRequest)
				.toList();
	}
}
