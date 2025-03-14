package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.ApiRequestService;
import javafx.scene.control.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestController {

    private final ApiRequestService apiRequestService;

    @Autowired
    public RequestController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public String submitRequest(String text, String method) {
        apiRequestService.submitRequest(ApiRequest.builder().url("").method(method).build());
        System.out.println(method + ":" + text);
        return "RESPONSE SUCCESSFUL";
    }
}
