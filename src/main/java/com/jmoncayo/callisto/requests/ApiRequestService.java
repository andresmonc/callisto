package com.jmoncayo.callisto.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiRequestService {


    private WebClient httpClient;

    @Autowired
    public ApiRequestService() {
        this.httpClient = WebClient.builder().build();
    }

    public void submitRequest(){

    }
}
