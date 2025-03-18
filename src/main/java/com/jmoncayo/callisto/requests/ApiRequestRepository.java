package com.jmoncayo.callisto.requests;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApiRequestRepository {
    private Map<String, ApiRequest> requests = new HashMap<>();

    public ApiRequestRepository() {
    }

    public ApiRequest getApiRequest(String requestUUID) {
        return requests.get(requestUUID);
    }

    public void update(ApiRequest request) {
        requests.put(request.getId(), request);
    }

    public List<ApiRequest> getActiveRequests() {
        return requests.values().stream().filter(request -> request.isActive()).toList();
    }

    public List<ApiRequest> getAllRequests() {
        return requests.values().stream().toList();
    }

    public void putAll(List<ApiRequest> requests) {
        if(requests ==null){
            return;
        }
        this.requests.putAll(requests.stream().collect(Collectors.toMap(ApiRequest::getId, o -> o)));
    }
}
