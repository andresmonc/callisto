package com.jmoncayo.callisto.ui.customcomponents;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Log4j2
public class KVDTableView extends TableView<TableEntry> {


	public KVDTableView() {
		initializeTable();
	}

	private void initializeTable() {
		// Enable/Disable Column
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		TableColumn<TableEntry, Boolean> enableColumn = new TableColumn<>();
		enableColumn.setMaxWidth(40);
		enableColumn.setCellValueFactory(cellData -> cellData.getValue().enabledProperty());
		enableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(enableColumn));

		// Key Column (Editable)
		TableColumn<TableEntry, String> keyColumn = new TableColumn<>("Key");
		keyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
		keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		keyColumn.setOnEditCommit(this::handleUpdate);

		// Value Column (Editable)
		TableColumn<TableEntry, String> valueColumn = new TableColumn<>("Value");
		valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
		valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		valueColumn.setOnEditCommit(this::handleUpdate);

		// Description Column (Starts as Label, Edits on Double Click)
		TableColumn<TableEntry, String> descriptionColumn = new TableColumn<>("Description");
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		descriptionColumn.setCellFactory(col -> new TableCell<>() {
			private final Label label = new Label();
			private final TextField textField = new TextField();
			private final Button deleteButton = new Button("X");
			private final Region spacer = new Region(); // Pushes delete button to the right

			{
				HBox.setHgrow(spacer, Priority.ALWAYS); // Makes the spacer expand
				deleteButton.setStyle(
						"-fx-background-color: red; -fx-text-fill: white; -fx-padding: 2 5; -fx-font-size: 10px;");
				deleteButton.setOnAction(
						e -> getTableView().getItems().remove(getTableRow().getItem()));

				textField.setOnAction(e -> commitEdit(textField.getText()));
				textField.setOnKeyPressed(e -> {
					if (e.getCode() == KeyCode.ESCAPE) cancelEdit();
				});

				setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && !isEmpty()) {
						startEdit();
					}
				});
			}

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || getTableRow() == null || getTableRow().getItem() == null) {
					setGraphic(null);
				} else {
					label.setText(item);
					textField.setText(item);
					HBox container = new HBox(5, label, spacer, deleteButton);
					container.setAlignment(Pos.CENTER_LEFT); // Keeps the label left-aligned
					setGraphic(container);
				}
			}

			@Override
			public void startEdit() {
				super.startEdit();
				setGraphic(new HBox(5, textField, deleteButton));
				textField.requestFocus();
			}

			@Override
			public void commitEdit(String newValue) {
				super.commitEdit(newValue);
				getTableRow().getItem().setDescription(newValue);
				updateItem(newValue, false);
			}

			@Override
			public void cancelEdit() {
				super.cancelEdit();
				updateItem(getItem(), false);
			}
		});
		descriptionColumn.setOnEditCommit(this::handleUpdate);

		// Add columns to table
		getColumns().addAll(enableColumn, keyColumn, valueColumn, descriptionColumn);

		// Allow editing
		setEditable(true);
		TableEntry tableEntry = new TableEntry(true, "Key", "Value", "Description", true);
		this.getItems().add(tableEntry);
	}

	public void handleUpdate(TableColumn.CellEditEvent<TableEntry, String> event) {
		log.info("updated");
		TableEntry entry = event.getRowValue();
		String newValue = event.getNewValue();
		// Check if the row being edited is the placeholder row
		if (entry.getPlaceholder().get()) {
			// No longer a placeholder
			entry.getPlaceholder().set(false);
			// Add a new placeholder row after the edit
			TableEntry placeholderRow = new TableEntry(true, "Key", "Value", "Description", true);
			getItems().add(placeholderRow);
		}

		// Update the actual row based on the property being edited
		switch (event.getTableColumn().getText()) {
			case "Key" -> entry.setKey(newValue);
			case "Value" -> entry.setValue(newValue);
			case "Description" -> entry.setDescription(newValue);
		}
	}
}
