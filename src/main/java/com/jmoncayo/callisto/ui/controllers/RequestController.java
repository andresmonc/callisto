package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.ApiRequestService;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.requestview.tabs.HeadersTabView;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RequestController {

    private final ApiRequestService apiRequestService;
    ObservableList<HeadersTabView.Header> headers;


    @Autowired
    public RequestController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public Mono<String> submitRequest(String url, String method) {
        return apiRequestService.submitRequest(
                ApiRequest.builder()
                        .url(url)
                        .headers(headers.stream()
                                .filter(header -> !header.isPlaceholder())
                                .map(header -> Header.builder()
                                        .key(header.getKey())
                                        .value(header.getValue())
                                        .description(header.getDescription())
                                        .build()).toList()
                        )
                        .method(method).build());
    }

    public void setHeaders(ObservableList<HeadersTabView.Header> headers) {
        this.headers = headers;
    }
}
