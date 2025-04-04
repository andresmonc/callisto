package com.jmoncayo.callisto.ui.requestview;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.ui.controllers.RequestController;
import com.jmoncayo.callisto.ui.requestview.tabs.EditableTabPane;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestsViewer extends AnchorPane {
	private final ObjectFactory<RequestView> requestViewObjectFactory;
	private final TabPane tabs;
	private final RequestController requestController;

	@Autowired
	public RequestsViewer(
			ObjectFactory<RequestView> requestViewObjectFactory,
			RequestController requestController,
			EditableTabPane tabPane) {
		this.requestViewObjectFactory = requestViewObjectFactory;
		this.tabs = tabPane;
		requestController.watchTabNameChange(tabPane);
		this.requestController = requestController;
		final Button addButton = new Button("+");
		addButton.getStyleClass().add("request-tab-pane-add-button");
		addButton.setOnAction(event -> tabs.getTabs().add(emptyTab()));
		AnchorPane.setLeftAnchor(tabs, 0.0);
		AnchorPane.setRightAnchor(tabs, 0.0);
		AnchorPane.setTopAnchor(addButton, 1.0);
		AnchorPane.setRightAnchor(addButton, 5.0);
		this.getChildren().addAll(tabs, addButton);
		loadRequests();
		requestController.updateCurrentRequest(
				tabPane.getSelectionModel().getSelectedItem().getId());
	}

	public void loadRequests() {
		List<ApiRequest> activeRequests = requestController.getActiveRequests();
		activeRequests.forEach(request -> {
			Tab tab = newTab(request);
			tab.setId(request.getId());
			tabs.getTabs().add(tab);
		});
		if (activeRequests.isEmpty()) {
			ApiRequest request = requestController.createRequest();
			Tab tab = emptyTab();
			tab.setId(request.getId());
			tabs.getTabs().add(tab);
		}
	}

	private Tab createTab(ApiRequest request) {
		var tab = new Tab(request.getName() != null ? request.getName() : "Untitled");
		RequestView requestView = requestViewObjectFactory.getObject();
		requestView.initialize(request);
		requestView.setRequestUUID(request.getId());
		tab.setContent(requestView);
		// notify the request controller of who is on display
		requestController.updateCurrentRequest(request.getId());
		tab.setOnClosed(event -> requestController.closeRequest(requestView.getRequestUUID()));
		// Add listener to handle when the tab becomes active
		tab.setOnSelectionChanged(event -> {
			if (tab.isSelected()) {
				// Tab is selected (active)
				requestController.updateCurrentRequest(request.getId());
			}
		});
		return tab;
	}

	public Tab newTab(ApiRequest request) {
		return createTab(request);
	}

	private Tab emptyTab() {
		ApiRequest request = requestController.createRequest();
		return createTab(request);
	}
}
