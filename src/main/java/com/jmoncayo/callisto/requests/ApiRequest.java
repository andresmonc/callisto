package com.jmoncayo.callisto.requests;

import com.jmoncayo.callisto.ui.requestview.tabs.HeadersTabView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.List;

@Builder
@Getter
@Setter
public class ApiRequest {
    private final HttpMethod method;
    private final String url;
    private final String body;
    private final List<Header> headers;

    public static class ApiRequestBuilder {
        public ApiRequestBuilder method(String method) {
            this.method = HttpMethod.valueOf(method.toUpperCase());
            return this;
        }
    }
}