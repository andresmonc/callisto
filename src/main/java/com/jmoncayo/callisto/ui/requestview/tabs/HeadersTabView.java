package com.jmoncayo.callisto.ui.requestview.tabs;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.controllers.RequestController;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@Log4j2
public class HeadersTabView extends StackPane {

	@Getter
	private final TableView<HeaderRow> tableView;

	private final RequestController requestController;

	public HeadersTabView(RequestController requestController) {
		this.requestController = requestController;
		this.tableView = new TableView<>();
		initTableView(tableView);
		this.getChildren().add(tableView);
		tableView.getItems().add(new HeaderRow("Authorization", "Bearer token", "Authentication token"));
	}

	private void addPropertyListeners(ObservableList<HeaderRow> headerObservableList) {
		headerObservableList.addListener((javafx.collections.ListChangeListener<HeaderRow>)
				change -> requestController.updateAllHeaders(headerObservableList));
		for (HeaderRow header : headerObservableList) {
			header.key.addListener(
					(observable, oldValue, newValue) -> requestController.updateAllHeaders(headerObservableList));
			header.value.addListener(
					(observable, oldValue, newValue) -> requestController.updateAllHeaders(headerObservableList));
			header.description.addListener(
					(observable, oldValue, newValue) -> requestController.updateAllHeaders(headerObservableList));
		}
	}

	private void initTableView(TableView<HeaderRow> tableView) {
		// Create columns
		TableColumn<HeaderRow, String> keyColumn = createEditableColumn("Key", "key");
		TableColumn<HeaderRow, String> valueColumn = createEditableColumn("Value", "value");
		TableColumn<HeaderRow, String> descriptionColumn = createEditableColumn("Description", "description");
		// Set table resize policy
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		// Add columns to the table
		tableView.getColumns().add(keyColumn);
		tableView.getColumns().add(valueColumn);
		tableView.getColumns().add(descriptionColumn);
		// Make the TableView editable
		tableView.setEditable(true);
	}

	private TableColumn<HeaderRow, String> createEditableColumn(String columnName, String property) {
		TableColumn<HeaderRow, String> column = new TableColumn<>(columnName);
		column.setCellValueFactory(new PropertyValueFactory<>(property));
		column.setCellFactory(TextFieldTableCell.forTableColumn());

		// Add edit commit handlers to update the model
		column.setOnEditCommit(event -> {
			HeaderRow header = event.getRowValue();
			String newValue = event.getNewValue();

			// Check if the row being edited is the placeholder row
			if (header.isPlaceholder()) {
				// No longer a placeholder
				header.setPlaceholder(false);
				// Add a new placeholder row after the edit
				getTableView().getItems().add(new HeaderRow("Key", "Value", "Description"));
			}

			// Update the actual row based on the property being edited
			switch (property) {
				case "key" -> header.setKey(newValue);
				case "value" -> header.setValue(newValue);
				case "description" -> header.setDescription(newValue);
			}
		});
		return column;
	}

	public void initialize(ApiRequest request) {
		if (request.getHeaders() != null) {
			for (Header header : request.getHeaders()) {
				tableView.getItems().add(HeaderRow.fromHeader(header));
			}
		}
	}
}
