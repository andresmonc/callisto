package com.jmoncayo.callisto.ui.requestview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.codearea.AutoDetectCodeArea;
import com.jmoncayo.callisto.ui.controllers.RequestController;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestView extends VBox {

	private final RequestController requestController;
	private final RequestField requestField;
	private final RequestDetails tabsComponent;
	private final ResponseArea responseArea;

	@Setter
	@Getter
	private String requestUUID;

	public RequestView(
			RequestController requestController,
			RequestField requestField,
			RequestDetails tabsComponent,
			ResponseArea responseArea) {
		this.requestController = requestController;
		this.requestField = requestField;
		this.tabsComponent = tabsComponent;
		this.responseArea = responseArea;
		// Set up the RequestField and TabsComponent in a VBox
		this.getChildren().addAll(requestField, tabsComponent);
		// Create a SplitPane for the resizable area (between RequestField/Tabs and Response area)
		SplitPane splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.VERTICAL);
		// Create the top section that contains RequestField and TabsComponent
		VBox topSection = new VBox();
		topSection.getChildren().addAll(requestField, tabsComponent);
		// Add the top section and response area to the SplitPane
		splitPane.getItems().addAll(topSection, responseArea);
		// Set the SplitPane's divider position to be resizeable (set divider position between 0.8 and 1.0)
		splitPane.setDividerPositions(0.2);
		// Add the SplitPane to the main VBox
		this.getChildren().add(splitPane);
		// Set the VBox to stretch in width
		this.setFillWidth(true);

		requestField.getActionButton().setOnAction(event -> handleRequest());
		requestField
				.getMethod()
				.setOnAction(event -> requestController.updateRequestMethod(
						requestField.getMethod().getValue(), requestUUID));
		requestField
				.getRequestURL()
				.textProperty()
				.addListener(
						(observable, oldValue, newValue) -> requestController.updateRequestUrl(newValue, requestUUID));
	}

	private void handleRequest() {
		String url = requestField.getRequestURL().getText();
		String method = requestField.getMethod().getValue();
		AutoDetectCodeArea responseDisplay = responseArea.getResponseDisplay();
		if (url == null || url.isEmpty()) {
			responseDisplay.replaceText("Error: URL cannot be empty.");
			return;
		}
		if (method == null) {
			responseDisplay.replaceText("Error: Method must be selected.");
		}
		responseDisplay.replaceText("Sending request...");
		requestController
				.submitRequest()
				.subscribe(
						response -> Platform.runLater(() -> {
							responseArea.htmlPreview(response);
							responseArea.rawPreview(response);
						}),
						error -> Platform.runLater(() -> responseDisplay.replaceText("Error: " + error.getMessage())));
	}

	public void initialize(ApiRequest request) {
		// Must set id first as change listeners from below will try to read
		requestUUID = request.getId();
		requestField.getRequestURL().setText(request.getUrl());
		requestField.getMethod().setValue(request.getMethod());
		// Todo: update headers somehow
		List<Header> headers = request.getHeaders();
		tabsComponent.getHeadersTab().getHeadersTabView().initialize(request);
	}
}
