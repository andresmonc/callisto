package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
public class ApiRequest {
    private final String id = UUID.randomUUID().toString();
    private final HttpMethod method;
    private final String url;
    private final String body;
    private final List<Header> headers;

    public static class ApiRequestBuilder {
        // Change the parameter type to HttpMethod directly
        public ApiRequestBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        // Optionally, you can keep the String-based method for convenience
        public ApiRequestBuilder method(String method) {
            this.method = HttpMethod.valueOf(method.toUpperCase());
            return this;
        }
    }
}
