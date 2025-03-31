package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.ApiRequestService;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.requestview.tabs.EditableTabPane;
import com.jmoncayo.callisto.ui.requestview.tabs.HeaderRow;
import java.util.List;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class RequestController {

	private final ApiRequestService apiRequestService;

	private String activeRequestUUID = "default";

	@Autowired
	public RequestController(ApiRequestService apiRequestService) {
		this.apiRequestService = apiRequestService;
	}

	public Mono<String> submitRequest() {
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

	public void updateAllHeaders(ObservableList<HeaderRow> headerObservableList, String id) {
		List<Header> headers = headerObservableList.stream()
				.filter(header -> !header.isPlaceholder())
				.map(header -> Header.builder()
						.key(header.getKey())
						.value(header.getValue())
						.description(header.getDescription())
						.build())
				.toList();
		apiRequestService.updateHeaders(activeRequestUUID, headers);
		log.info("updating headers");
	}

	public void watchTabNameChange(EditableTabPane tabPane) {
		tabPane.tabNameProperty().addListener((observable, oldValue, newValue) -> {
			log.info(newValue);
			log.info(activeRequestUUID);
			apiRequestService.updateName(newValue, activeRequestUUID);
		});
	}

	//	public void updateAllParameters(ObservableList<ParamsTabView.ParamEntry> items, String requestId) {
	//		List<Parameter> parameters = items.stream()
	//				.filter(param -> !param.isPlaceholder)
	//				.map(paramEntry -> {
	//					var parameter = new Parameter();
	//					parameter.setDescription(paramEntry.descriptionProperty().get());
	//					parameter.setEnabled(paramEntry.enabledProperty().get());
	//					parameter.setKey(paramEntry.keyProperty().get());
	//					parameter.setValue(paramEntry.valueProperty().getValue());
	//					return parameter;
	//				})
	//				.toList();
	//		//		apiRequestService.updateHeaders(activeRequestUUID, headers);
	//		System.out.println("updating parameters");
	//		System.out.println(parameters);
	//	}
}
