package com.jmoncayo.callisto.ui.requestview.tabs;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.layout.StackPane;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Log4j2
public class ParamsTabView extends StackPane {

	private final TableView<ParamEntry> tableView = new TableView<>();

	public ParamsTabView() {
		initializeTable();
		getChildren().add(tableView);
	}

	private void initializeTable() {
		// Enable/Disable Column
		TableColumn<ParamEntry, Boolean> enableColumn = new TableColumn<>("Enable");
		enableColumn.setCellValueFactory(cellData -> cellData.getValue().enabledProperty());
		enableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(enableColumn));

		// Key Column (Editable)
		TableColumn<ParamEntry, String> keyColumn = new TableColumn<>("Key");
		keyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
		keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		keyColumn.setOnEditCommit(event -> event.getRowValue().setKey(event.getNewValue()));

		// Value Column (Editable)
		TableColumn<ParamEntry, String> valueColumn = new TableColumn<>("Value");
		valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
		valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		valueColumn.setOnEditCommit(event -> event.getRowValue().setValue(event.getNewValue()));

		// Description Column (Starts as Label, Edits on Double Click)
		TableColumn<ParamEntry, String> descriptionColumn = new TableColumn<>("Description");
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
						e -> tableView.getItems().remove(getTableRow().getItem()));

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

		// Add columns to table
		tableView.getColumns().addAll(enableColumn, keyColumn, valueColumn, descriptionColumn);

		// Allow editing
		tableView.setEditable(true);

		// Add sample data
		tableView
				.getItems()
				.addAll(
						new ParamEntry(true, "apiKey", "12345", "API authentication key"),
						new ParamEntry(false, "timeout", "5000", "Request timeout in ms"));
	}

	// Model class for table entries
	public static class ParamEntry {
		private final BooleanProperty enabled = new SimpleBooleanProperty();
		private final StringProperty key = new SimpleStringProperty();
		private final StringProperty value = new SimpleStringProperty();
		private final StringProperty description = new SimpleStringProperty();

		public ParamEntry(boolean enabled, String key, String value, String description) {
			this.enabled.set(enabled);
			this.key.set(key);
			this.value.set(value);
			this.description.set(description);
		}

		public BooleanProperty enabledProperty() {
			return enabled;
		}

		public StringProperty keyProperty() {
			return key;
		}

		public StringProperty valueProperty() {
			return value;
		}

		public StringProperty descriptionProperty() {
			return description;
		}

		public void setKey(String key) {
			this.key.set(key);
		}

		public void setValue(String value) {
			this.value.set(value);
		}

		public void setDescription(String description) {
			this.description.set(description);
		}
	}
}
