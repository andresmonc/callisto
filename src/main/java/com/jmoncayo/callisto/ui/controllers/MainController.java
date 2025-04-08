package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.ui.requestview.RequestCollectionViewer;
import com.jmoncayo.callisto.ui.sidenavigation.SideNavigationPanel;
import com.jmoncayo.callisto.ui.toolbar.Toolbar;
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
	public MainController(Toolbar toolbar, SideNavigationPanel sideNav, SplitPane splitPane, RequestCollectionViewer requestViewer) {
		// Create the main content container (StackPane for dynamic view switching)
		contentStack = new StackPane();
		contentStack.setPrefWidth(Double.MAX_VALUE); // nothing
		contentStack.setPrefHeight(Double.MAX_VALUE); // nothing

		// Add the RequestsViewer
		contentStack.getChildren().add(requestViewer);

		// Initialize the SplitPane (add the side navigation and main content area)
		splitPane.getItems().addAll(sideNav, contentStack);
		splitPane.setDividerPositions(.20); // Set the divider position for the SplitPane
		splitPane.setPrefHeight(Double.MAX_VALUE); // nothing

		// Create the root BorderPane and place the SplitPane in the center
		root = new BorderPane();
		root.setTop(toolbar);
		root.setLeft(sideNav); // Sidebar stays on the left
		root.setCenter(splitPane); // Main content will be swapped in the center
	}

	public BorderPane getRoot() {
		return root;
	}
}
