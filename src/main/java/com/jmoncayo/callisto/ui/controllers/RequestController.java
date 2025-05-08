package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.ApiRequestService;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.requests.Parameter;
import com.jmoncayo.callisto.ui.customcomponents.TableEntry;
import com.jmoncayo.callisto.ui.events.DeleteRequestEvent;
import com.jmoncayo.callisto.ui.events.RequestRenamedEvent;
import com.jmoncayo.callisto.ui.requestview.tabs.EditableTabPane;
import java.util.List;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class RequestController {

	private final ApiRequestService apiRequestService;
	private final ApplicationEventPublisher eventPublisher;

	@Getter
	private String activeRequestId = "default";

	@Autowired
	public RequestController(ApiRequestService apiRequestService, ApplicationEventPublisher eventPublisher) {
		this.apiRequestService = apiRequestService;
		this.eventPublisher = eventPublisher;
	}

	public Mono<String> submitRequest() {
		return apiRequestService.submitRequest(activeRequestId);
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
		System.out.println(activeRequestId);
		activeRequestId = id;
	}

	public void updateAllHeaders(ObservableList<TableEntry> headerObservableList) {
		List<Header> headers = headerObservableList.stream()
				.filter(header -> !header.getPlaceholder().get())
				.map(header -> Header.builder()
						.key(header.getKey().get())
						.value(header.getValue().get())
						.description(header.getDescription().get())
						.placeholder(header.getPlaceholder().get())
						.enabled(header.getEnabled().get())
						.build())
				.toList();
		apiRequestService.updateHeaders(activeRequestId, headers);
		log.info("updating headers");
	}

	public void watchTabNameChange(EditableTabPane tabPane) {
		tabPane.tabNameProperty().addListener((observable, oldValue, newValue) -> {
			log.info(newValue);
			log.info(activeRequestId);
			apiRequestService.updateName(newValue, activeRequestId);
			eventPublisher.publishEvent(new RequestRenamedEvent(this, activeRequestId, newValue));
		});
	}

	public void updateAllParameters(ObservableList<TableEntry> items) {
		log.info("parameter update called.");
		List<Parameter> parameters = items.stream()
				.filter(header -> !header.getPlaceholder().get())
				.map(header -> Parameter.builder()
						.key(header.getKey().get())
						.value(header.getValue().get())
						.description(header.getDescription().get())
						.placeholder(header.getPlaceholder().get())
						.enabled(header.getEnabled().get())
						.build())
				.toList();
		apiRequestService.updateParameters(activeRequestId, parameters);
		log.info("updating headers");
	}

	public ApiRequest getActiveRequest() {
		return apiRequestService.getRequest(activeRequestId);
	}

	public ApiRequest getRequest(String requestId) {
		return apiRequestService.getRequest(requestId);
	}

	public void openRequest(String requestId) {
		apiRequestService.openRequest(requestId);
	}

	@EventListener(DeleteRequestEvent.class)
	public void deleteEventListener(DeleteRequestEvent deleteRequestEvent) {
		String id = deleteRequestEvent.getId();
		log.info("Deleting request with ID: " + id);
		apiRequestService.deleteRequest(id);
	}

	public void saveRequest(String id) {
		ApiRequest request = apiRequestService.getRequest(id);
		if (request != null) {
			apiRequestService.saveRequest(request);
			log.info("Saved request with ID: " + id);
		} else {
			log.warn("Request with ID: " + id + " not found.");
		}
	}

	public List<ApiRequest> getRequestsForCollectionID(String id) {
		List<ApiRequest> requests = apiRequestService.getRequestsForCollectionID(id);
		if (requests != null) {
			log.info("Found " + requests.size() + " requests for collection ID: " + id);
			return requests;
		} else {
			log.warn("No requests found for collection ID: " + id);
			return List.of();
		}
	}
}
