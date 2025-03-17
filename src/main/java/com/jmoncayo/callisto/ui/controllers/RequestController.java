package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.ApiRequestService;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.requestview.tabs.HeadersTabView;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RequestController {

    private final ApiRequestService apiRequestService;

    @Autowired
    public RequestController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public Mono<String> submitRequest(String url, String method, String activeRequestUUID) {
        // send UUID somehow - don't send all the details through like this

        return apiRequestService.submitRequest(
                ApiRequest.builder()
                        .url(url)
                        .method(method).build());
    }

    public void createHeaderChangeListener(ObservableList<HeadersTabView.Header> headers) {
        headers.addListener((ListChangeListener<? super HeadersTabView.Header>) c ->
        {
            String requestUUID = "";
            var requestHeaders = c.getList().stream()
                    .filter(header -> !header.isPlaceholder())
                    .map(header -> Header.builder()
                            .key(header.getKey())
                            .value(header.getValue())
                            .description(header.getDescription())
                            .build()).toList();
            apiRequestService.updateHeaders(requestUUID, requestHeaders);
        });
    }
}
