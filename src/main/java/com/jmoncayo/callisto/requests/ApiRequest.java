package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Builder
@Getter
@Setter
public class ApiRequest {
    private final HttpMethod method;
    private final String url;
    private final String body;

    public static class ApiRequestBuilder {
        public ApiRequestBuilder method(String method) {
            this.method = HttpMethod.valueOf(method.toUpperCase());
            return this;
        }
    }
}