package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.ui.requestview.RequestsViewer;
import com.jmoncayo.callisto.ui.sidenavigation.SideNavigationPanel;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController {
	private StackPane contentStack;
	private BorderPane root;

	@Autowired
	public MainController(SideNavigationPanel sideNav, SplitPane splitPane, RequestsViewer requestViewer) {
		// Create the main content container (StackPane for dynamic view switching)
		contentStack = new StackPane();
		contentStack.setPrefWidth(Double.MAX_VALUE); // nothing
		contentStack.setPrefHeight(Double.MAX_VALUE); // nothing

		// Add the RequestsViewer
		contentStack.getChildren().add(requestViewer);

		// Initialize the SplitPane (add the side navigation and main content area)
		splitPane.getItems().addAll(sideNav, contentStack);
		splitPane.setDividerPositions(0.15); // Set the divider position for the SplitPane
		splitPane.setPrefHeight(Double.MAX_VALUE); // nothing

		// Create the root BorderPane and place the SplitPane in the center
		root = new BorderPane();
		root.setLeft(sideNav); // Sidebar stays on the left
		root.setCenter(splitPane); // Main content will be swapped in the center
	}

	// Method to switch views
	public void switchView(javafx.scene.Node newView) {
		contentStack.getChildren().clear(); // Remove previous view
		contentStack.getChildren().add(newView); // Add new view
	}

	public BorderPane getRoot() {
		return root;
	}
}
