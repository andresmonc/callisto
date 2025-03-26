package com.jmoncayo.callisto.requests;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiRequestService {

	private final ApiRequestRepository requestRepository;
	private final WebClient httpClient;

	private static final ApiRequest.ApiRequestBuilder DEFAULT_REQUEST = ApiRequest.builder()
			.method(String.valueOf(HttpMethod.GET))
			.active(true)
			.name("Untitled");

	@Autowired
	public ApiRequestService(ApiRequestRepository requestRepository) {
		this.requestRepository = requestRepository;
		this.httpClient = WebClient.builder().build();
	}

	public Mono<String> submitRequest(String requestUUID) {
		ApiRequest request = requestRepository.getApiRequest(requestUUID);
		return httpClient
				.method(HttpMethod.valueOf(request.getMethod()))
				.uri(request.getUrl())
				.headers(httpHeaders -> {
					if (request.getHeaders() != null) {
						request.getHeaders()
								.forEach(header -> httpHeaders.put(header.getKey(), List.of(header.getValue())));
					}
				})
				.retrieve()
				.bodyToMono(String.class)
				.onErrorReturn("Request failed"); // Handle errors gracefully
	}

	public void updateHeaders(String requestUUID, List<Header> requestHeaders) {
		System.out.println("updating headers for: " + requestUUID);
		ApiRequest request = requestRepository.getApiRequest(requestUUID);
		if (request == null) {
			request = DEFAULT_REQUEST.build();
		}
		requestRepository.update(request.toBuilder().headers(requestHeaders).build());
	}

	public void updateUrl(String url, String requestUUID) {
		System.out.println(requestUUID);
		ApiRequest request = requestRepository.getApiRequest(requestUUID);
		if (request == null) {
			request = DEFAULT_REQUEST.build();
		}
		requestRepository.update(request.toBuilder().url(url).build());
	}

	public void updateMethod(String method, String requestUUID) {
		ApiRequest request = requestRepository.getApiRequest(requestUUID);
		if (request == null) {
			request = DEFAULT_REQUEST.build();
		}
		requestRepository.update(request.toBuilder().method(method).build());
	}

	public ApiRequest getRequest(String requestUUID) {
		ApiRequest request = requestRepository.getApiRequest(requestUUID);
		if (request == null) {
			request = DEFAULT_REQUEST.build();
			requestRepository.update(request);
		}
		return request;
	}

	public List<ApiRequest> getActiveRequests() {
		return requestRepository.getActiveRequests();
	}

	public List<ApiRequest> getAllRequests() {
		return requestRepository.getAllRequests();
	}

	public void closeRequest(String requestUUID) {
		ApiRequest request = requestRepository.getApiRequest(requestUUID);
		if (request != null) {
			requestRepository.update(request.toBuilder().active(false).build());
		}
	}

	public void load(List<ApiRequest> requests) {
		requestRepository.putAll(requests);
	}

	/**
	 * Creates a default request which will contain a generated UUID
	 *
	 * @return a default request
	 */
	public ApiRequest createRequest() {
		ApiRequest newDefaultRequest = DEFAULT_REQUEST.build();
		requestRepository.update(newDefaultRequest);
		return newDefaultRequest;
	}

	public void updateName(String name, String id) {
		ApiRequest request = requestRepository.getApiRequest(id);
		if (request != null) {
			requestRepository.update(request.toBuilder().name(name).build());
		}
	}
}
