package com.jmoncayo.callisto.requests;

import lombok.Builder;
import org.springframework.http.HttpMethod;

@Builder
public class ApiRequest {
    private final HttpMethod method;
    private final String url;

    public static class ApiRequestBuilder {
        public ApiRequestBuilder method(String method) {
            this.method = HttpMethod.valueOf(method.toUpperCase());
            return this;
        }
    }
}