package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.springframework.stereotype.Component;

@Component
public class EditableTabPane extends TabPane {

	public EditableTabPane() {
		// Add a listener to each tab for double-click edit functionality
		setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) { // Detect double-click
				Tab clickedTab = getSelectionModel().getSelectedItem();
				if (clickedTab != null) {
					enableTabNameEditing(clickedTab);
				}
			}
		});
	}

	private void enableTabNameEditing(Tab tab) {
		TextField textField = new TextField(tab.getText());
		textField.setOnAction(e -> {
			tab.setText(textField.getText()); // Set new tab name
			tab.setGraphic(null); // Remove the text field after editing
		});
		textField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				tab.setGraphic(null);
			}
		});
		tab.setGraphic(textField); // Replace the tab header with a text field
		textField.requestFocus(); // Focus on the text field
	}
}
