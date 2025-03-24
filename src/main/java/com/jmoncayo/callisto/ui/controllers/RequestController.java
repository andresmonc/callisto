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

import java.util.List;

@Component
public class RequestController {

    private final ApiRequestService apiRequestService;

    private String activeRequestUUID = "default";

    @Autowired
    public RequestController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public Mono<String> submitRequest() {
        // send UUID somehow - don't send all the details through like this
        return apiRequestService.submitRequest(activeRequestUUID);
    }

    public void updateRequestUrl(String committed) {
        apiRequestService.updateUrl(committed, activeRequestUUID);
    }

    public void updateRequestMethod(String string) {
        apiRequestService.updateMethod(string, activeRequestUUID);
    }

    public List<ApiRequest> getActiveRequests() {
        return apiRequestService.getActiveRequests();
    }

    public void closeRequest(String requestUUID) {
        apiRequestService.closeRequest(requestUUID);
    }

    public ApiRequest createRequest() {
        return apiRequestService.createRequest();
    }

    public void updateCurrentRequest(String id) {
        System.out.println(activeRequestUUID);
        activeRequestUUID = id;
    }

    public void updateAllHeaders(ObservableList<HeadersTabView.Header> headerObservableList) {
        List<Header> headers = headerObservableList.stream()
                .filter(header -> !header.isPlaceholder())
                .map(header -> Header.builder()
                        .key(header.getKey())
                        .value(header.getValue())
                        .description(header.getDescription())
                        .build()).toList();
        apiRequestService.updateHeaders(activeRequestUUID, headers);
        System.out.println("updating headers");
    }
}

