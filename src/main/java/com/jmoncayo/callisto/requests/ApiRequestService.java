package com.jmoncayo.callisto.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiRequestService {


    private WebClient httpClient;

    @Autowired
    public ApiRequestService() {
        this.httpClient = WebClient.builder().build();
    }

    public Mono<String> submitRequest(ApiRequest request) {
        return httpClient.method(request.getMethod())
                .uri(request.getUrl())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("Request failed"); // Handle errors gracefully
    }
}
