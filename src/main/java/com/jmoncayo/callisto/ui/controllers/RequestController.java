package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.ApiRequestService;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.requestview.tabs.EditableTabPane;
import com.jmoncayo.callisto.ui.requestview.tabs.HeaderRow;
import java.util.List;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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

	public void updateRequestUrl(String committed, String requestUUID) {
		apiRequestService.updateUrl(committed, requestUUID);
	}

	public void updateRequestMethod(String string, String requestUUID) {
		apiRequestService.updateMethod(string, requestUUID);
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

	public void updateAllHeaders(ObservableList<HeaderRow> headerObservableList) {
		List<Header> headers = headerObservableList.stream()
				.filter(header -> !header.isPlaceholder())
				.map(header -> Header.builder()
						.key(header.getKey())
						.value(header.getValue())
						.description(header.getDescription())
						.build())
				.toList();
		apiRequestService.updateHeaders(activeRequestUUID, headers);
		System.out.println("updating headers");
	}

	public void watchTabNameChange(EditableTabPane tabPane) {
		tabPane.tabNameProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			System.out.println(activeRequestUUID);
			apiRequestService.updateName(newValue, activeRequestUUID);
		});
	}
}
