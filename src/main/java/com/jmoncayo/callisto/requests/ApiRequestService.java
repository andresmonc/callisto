package com.jmoncayo.callisto.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ApiRequestService {

    private final ApiRequestRepository requestRepository;
    private final WebClient httpClient;

    @Autowired
    public ApiRequestService(ApiRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
        this.httpClient = WebClient.builder().build();
    }

    public Mono<String> submitRequest(String requestUUID) {
        ApiRequest request = requestRepository.getApiRequest(requestUUID);
        return httpClient.method(request.getMethod())
                .uri(request.getUrl())
                .headers(httpHeaders -> {
                    if (request.getHeaders() != null) {
                        request.getHeaders().forEach(header -> httpHeaders.put(header.getKey(), List.of(header.getValue())));
                    }
                })
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("Request failed"); // Handle errors gracefully
    }

    public void updateHeaders(String requestUUID, List<Header> requestHeaders) {
        ApiRequest request = requestRepository.getApiRequest(requestUUID);
        if (request == null) {
            request = ApiRequest.builder().build();
        }
        requestRepository.update(request.toBuilder().headers(requestHeaders).build());
    }

    public void updateUrl(String url, String requestUUID) {
        System.out.println(requestUUID);
        ApiRequest request = requestRepository.getApiRequest(requestUUID);
        if (request == null) {
            request = ApiRequest.builder().build();
        }
        requestRepository.update(request.toBuilder().url(url).build());
    }

    public void updateMethod(String method, String requestUUID) {
        ApiRequest request = requestRepository.getApiRequest(requestUUID);
        if (request == null) {
            request = ApiRequest.builder().build();
        }
        requestRepository.update(request.toBuilder().method(method).build());
    }

    public ApiRequest getRequest(String requestUUID){
        ApiRequest request = requestRepository.getApiRequest(requestUUID);
        if (request == null) {
            request = ApiRequest.builder().build();
            requestRepository.update(request);
        }
        return request;
    }

    public List<ApiRequest> getActiveRequests() {
        return requestRepository.getActiveRequests();
    }

    public List<ApiRequest> getAllRequests() {
        return requestRepository.getAllRequests();
    }

    public void closeRequest(String requestUUID) {
        ApiRequest request = requestRepository.getApiRequest(requestUUID);
        if(request!=null){
            requestRepository.update(request.toBuilder().active(false).build());
        }
    }

    public void load(List<ApiRequest> requests) {
        requestRepository.putAll(requests);
    }
}
