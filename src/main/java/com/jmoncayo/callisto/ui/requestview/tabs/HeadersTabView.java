package com.jmoncayo.callisto.ui.requestview.tabs;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.controllers.RequestController;
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

	private String requestId;

	public HeadersTabView(RequestController requestController) {
		this.requestController = requestController;
		this.tableView = new TableView<>();
		initTableView(tableView);
		this.getChildren().add(tableView);
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
				HeaderRow placeholderRow = new HeaderRow("Key", "Value", "Description");
				placeholderRow.setPlaceholder(true);
				getTableView().getItems().add(placeholderRow);
			}

			// Update the actual row based on the property being edited
			switch (property) {
				case "key" -> header.setKey(newValue);
				case "value" -> header.setValue(newValue);
				case "description" -> header.setDescription(newValue);
			}
			requestController.updateAllHeaders(getTableView().getItems(), requestId);
		});
		return column;
	}

	public void initialize(ApiRequest request) {
		requestId = request.getId();
		if (request.getHeaders() != null) {
			for (Header header : request.getHeaders()) {
				HeaderRow headerRow = HeaderRow.fromHeader(header);
				tableView.getItems().add(headerRow);
			}
		}
		HeaderRow placeholderRow = new HeaderRow("Key", "Value", "Description");
		placeholderRow.setPlaceholder(true);
		tableView.getItems().add(placeholderRow);
		getTableView().getItems().addListener((javafx.collections.ListChangeListener<HeaderRow>)
				change -> requestController.updateAllHeaders(tableView.getItems(),request.getId()));
	}
}
