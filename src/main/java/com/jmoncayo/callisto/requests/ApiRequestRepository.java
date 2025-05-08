package com.jmoncayo.callisto.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class ApiRequestRepository {

	private final Map<String, ApiRequest> requests = new HashMap<>();

	public ApiRequestRepository() {}

	public ApiRequest getApiRequest(String requestUUID) {
		return requests.get(requestUUID);
	}

	public void update(ApiRequest request) {
		requests.put(request.getId(), request);
	}

	public List<ApiRequest> getActiveRequests() {
		return requests.values().stream().filter(ApiRequest::isActive).toList();
	}

	public List<ApiRequest> getAllRequests() {
		return requests.values().stream().toList();
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
}
