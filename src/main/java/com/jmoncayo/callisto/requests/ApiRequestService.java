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

    public Mono<String> submitRequest(ApiRequest request) {
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
}
