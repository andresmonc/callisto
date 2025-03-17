package com.jmoncayo.callisto.requests;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiRequestRepository {
    private Map<String, ApiRequest> requests = new HashMap<>();

    public ApiRequest getApiRequest(String requestUUID) {
        return requests.get(requestUUID);
    }

    public void update(ApiRequest request) {
        requests.put(request.getId(), request);
    }
}
