package com.jmoncayo.callisto.ui.sidenavigation;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SideNavigationPanel extends VBox {

	private boolean isCollapsed = false;

	private static final int MAX_WIDTH = 200;

	@Autowired
	public SideNavigationPanel(SplitPane splitPane, SideNavigationCollectionView sideNavigationCollectionView) {
		// set style class
		this.getStyleClass().add("side-nav");
		// Create the collapse button
		Button collapseButton = new Button("⬅"); // Or "▶" depending on the direction
		collapseButton.getStyleClass().add("collapse-button");
		collapseButton.setOnAction(e -> toggleCollapse());

		// Add the collapse button and other buttons
		this.getChildren().addAll(collapseButton, sideNavigationCollectionView);

		// Delay the listener setup until the SplitPane is fully initialized
		Platform.runLater(() -> {
			if (!splitPane.getDividers().isEmpty()) {
				splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
					isCollapsed = false;
					setVisible(true);
					setMaxWidth(MAX_WIDTH);
				});
			}
		});
	}

	private void toggleCollapse() {
		// Toggle the collapse state
		isCollapsed = !isCollapsed;

		// Adjust the panel's width or visibility based on the collapse state
		if (isCollapsed) {
			// Hide the contents and shrink the width
			this.setVisible(false); // Hides the content
			this.setMinWidth(0); // Shrinks the width to zero
			this.setMaxWidth(0); // Restrict the max width to zero
		}
	}
}
