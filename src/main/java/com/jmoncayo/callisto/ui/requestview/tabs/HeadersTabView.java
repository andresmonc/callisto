package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class HeadersTabView extends StackPane {

    public HeadersTabView() {
        // Create the title label
        Label titleLabel = createTitleLabel();

        // Create the TableView and set up the columns
        TableView<Header> tableView = createTableView();

        // Add some sample data (can be replaced later with actual data)
        populateTableData(tableView);

        // Set up layout
        VBox vbox = new VBox(titleLabel, tableView);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_CENTER);

        // Add VBox to StackPane
        this.getChildren().add(vbox);
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("Headers");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px;");
        return titleLabel;
    }

    private TableView<Header> createTableView() {
        TableView<Header> tableView = new TableView<>();

        // Create columns
        TableColumn<Header, String> keyColumn = createEditableColumn("Key", "key",tableView);
        TableColumn<Header, String> valueColumn = createEditableColumn("Value", "value",tableView);
        TableColumn<Header, String> descriptionColumn = createEditableColumn("Description", "description",tableView);

        // Set table resize policy
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Add columns to the table
        tableView.getColumns().addAll(keyColumn, valueColumn, descriptionColumn);

        // Make the TableView editable
        tableView.setEditable(true);

        return tableView;
    }

    private TableColumn<Header, String> createEditableColumn(String columnName, String property, TableView<Header> tableView) {
        TableColumn<Header, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        // Add edit commit handlers to update the model
        column.setOnEditCommit(event -> {
            Header header = event.getRowValue();
            String newValue = event.getNewValue();

            // Check if the row being edited is the placeholder row
            if ("Key".equals(header.getKey()) && "Value".equals(header.getValue()) && "Description".equals(header.getDescription())) {
                // a new placeholder row after the edit
                tableView.getItems().add(new Header("Key", "Value", "Description"));
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


    private void populateTableData(TableView<Header> tableView) {
        // Add some actual data (can be replaced with real data later)
        tableView.getItems().add(new Header("Authorization", "Bearer token", "Authentication token"));
        tableView.getItems().add(new Header("Content-Type", "application/json", "Type of content"));

        // Add a placeholder row if there isn't one already
        if (!hasPlaceholderRow(tableView)) {
            tableView.getItems().add(new Header("Key", "Value", "Description"));
        }
    }

    // Helper method to check if the placeholder row already exists
    private boolean hasPlaceholderRow(TableView<Header> tableView) {
        for (Header header : tableView.getItems()) {
            if ("Key".equals(header.getKey()) && "Value".equals(header.getValue()) && "Description".equals(header.getDescription())) {
                return true;
            }
        }
        return false;
    }

    public static class Header {
        private final SimpleStringProperty key;
        private final SimpleStringProperty value;
        private final SimpleStringProperty description;

        public Header(String key, String value, String description) {
            this.key = new SimpleStringProperty(key);
            this.value = new SimpleStringProperty(value);
            this.description = new SimpleStringProperty(description);
        }

        public String getKey() {
            return key.get();
        }

        public String getValue() {
            return value.get();
        }

        public String getDescription() {
            return description.get();
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