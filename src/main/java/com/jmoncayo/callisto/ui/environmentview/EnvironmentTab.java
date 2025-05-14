package com.jmoncayo.callisto.ui.environmentview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

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
	private final TableView<EnvironmentVariableRow> table;

	public EnvironmentTab() {
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

		table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<EnvironmentVariableRow, String> nameCol = new TableColumn<>("Variable");
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		TableColumn<EnvironmentVariableRow, String> typeCol = new TableColumn<>("Type");
		typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

		TableColumn<EnvironmentVariableRow, String> initialValCol = new TableColumn<>("Initial value");
		initialValCol.setCellValueFactory(cellData -> cellData.getValue().initialValueProperty());

		TableColumn<EnvironmentVariableRow, String> currentValCol = new TableColumn<>("Current value");
		currentValCol.setCellValueFactory(cellData -> cellData.getValue().currentValueProperty());

		TableColumn<EnvironmentVariableRow, Void> actionsCol = new TableColumn<>("");

		table.getColumns().addAll(nameCol, typeCol, initialValCol, currentValCol, actionsCol);

		VBox layout = new VBox(10, editableEnvironmentName, searchField, table);
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
	public static class EnvironmentVariableRow {
		private final StringProperty name = new SimpleStringProperty("");
		private final StringProperty type = new SimpleStringProperty("");
		private final StringProperty initialValue = new SimpleStringProperty("");
		private final StringProperty currentValue = new SimpleStringProperty("");

		public StringProperty nameProperty() { return name; }
		public StringProperty typeProperty() { return type; }
		public StringProperty initialValueProperty() { return initialValue; }
		public StringProperty currentValueProperty() { return currentValue; }
	}
}
