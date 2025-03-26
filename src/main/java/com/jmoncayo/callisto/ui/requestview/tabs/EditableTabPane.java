package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.springframework.stereotype.Component;

@Component
public class EditableTabPane extends TabPane {

	private final StringProperty tabNameProperty = new SimpleStringProperty();

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

	public StringProperty tabNameProperty() {
		return tabNameProperty;
	}

	private void enableTabNameEditing(Tab tab) {
		TextField textField = new TextField(tab.getText());

		textField.setOnAction(e -> finishEditing(tab, textField));
		textField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				tab.setGraphic(null);
			}
		});

		tab.setGraphic(textField);
		textField.requestFocus();
	}

	private void finishEditing(Tab tab, TextField textField) {
		String newName = textField.getText().trim().isEmpty() ? "Untitled" : textField.getText();
		tab.setText(newName);
		tab.setGraphic(null);
		tabNameProperty.set(newName); // Notify listeners
	}
}
