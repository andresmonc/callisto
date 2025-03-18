package com.jmoncayo.callisto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
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

    private final String id;
    private final HttpMethod method;
    private final String url;
    private final String body;
    private final List<Header> headers;
    private final String collectionId;
    private final boolean active;
    private final String name;

    @JsonCreator
    public ApiRequest(
            @JsonProperty("id") String id,
            @JsonProperty("method") HttpMethod method,
            @JsonProperty("url") String url,
            @JsonProperty("body") String body,
            @JsonProperty("headers") List<Header> headers,
            @JsonProperty("collectionId") String collectionId,
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name
    ) {
        this.id = id != null ? id : UUID.randomUUID().toString(); // Default value for id
        this.method = method;
        this.url = url;
        this.body = body;
        this.headers = headers;
        this.collectionId = collectionId;
        this.active = active;
        this.name = name;
    }

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
