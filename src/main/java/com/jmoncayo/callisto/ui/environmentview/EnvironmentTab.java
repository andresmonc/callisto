package com.jmoncayo.callisto.ui.environmentview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.ui.controllers.EnvironmentController;
import com.jmoncayo.callisto.ui.customcomponents.VariableTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class EnvironmentTab extends Tab {

	private final TextField editableEnvironmentName;
	private final EnvironmentController environmentController;

	public EnvironmentTab(EnvironmentController environmentController, VariableTableView variableTableView) {
		this.environmentController = environmentController;
		editableEnvironmentName = new TextField("New Environment");
		editableEnvironmentName.setEditable(false);
		editableEnvironmentName.setFocusTraversable(false);
		setTextFieldStyle(false);

		// When name changes, update the tab title
		editableEnvironmentName.textProperty().addListener((obs, oldText, newText) -> this.setText(newText));

		editableEnvironmentName.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				editableEnvironmentName.setEditable(true);
				editableEnvironmentName.setFocusTraversable(true);
				setTextFieldStyle(true);
				editableEnvironmentName.requestFocus();
				editableEnvironmentName.selectAll();
			}
		});

		editableEnvironmentName.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused) {
				editableEnvironmentName.setEditable(false);
				editableEnvironmentName.setFocusTraversable(false);
				setTextFieldStyle(false);
			}
		});

		TextField searchField = new TextField();
		searchField.setPromptText("Filter variables");

		VBox layout = new VBox(10, editableEnvironmentName, searchField);
		layout.setPadding(new Insets(20));

		StackPane stackPane = new StackPane(layout);
		this.setContent(stackPane);
		this.setClosable(true);
	}

	public void initNewEnvironment() {
		this.setText("New Environment");
		this.editableEnvironmentName.setText("New Environment");
	}

	private void setTextFieldStyle(boolean editing) {
		if (editing) {
			editableEnvironmentName.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-padding: 2 4 2 4;");
		} else {
			editableEnvironmentName.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 2 4 2 4;");
		}
	}

	// Placeholder for table row data

}
