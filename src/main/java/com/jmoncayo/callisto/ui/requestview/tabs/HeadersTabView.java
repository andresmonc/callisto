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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeadersTabView extends StackPane {

    @Autowired
    public HeadersTabView() {
        // Create the title label
        Label titleLabel = new Label("Headers");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px;");

        // Create the TableView and columns
        TableView<Header> tableView = new TableView<>();

        // Create the columns
        TableColumn<Header, String> keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Header, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Header, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Add columns to the table
        tableView.getColumns().add(keyColumn);
        tableView.getColumns().add(valueColumn);
        tableView.getColumns().add(descriptionColumn);

        // Add some sample data (You can replace this with actual data)
        tableView.getItems().add(new Header("Authorization", "Bearer token", "Authentication token"));
        tableView.getItems().add(new Header("Content-Type", "application/json", "Type of content"));

        // Set the TableView's preferred size
        descriptionColumn.setResizable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Create a VBox to hold the title and the table
        VBox vbox = new VBox(titleLabel, tableView);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_CENTER);

        // Add the VBox to the StackPane
        this.getChildren().add(vbox);

        // Editable
        // Make the TableView editable
        tableView.setEditable(true);

        // Set the columns to be editable
        keyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add edit commit handlers to update the model
        keyColumn.setOnEditCommit(event -> {
            Header header = event.getRowValue();
            header.setKey(event.getNewValue());
        });
        valueColumn.setOnEditCommit(event -> {
            Header header = event.getRowValue();
            header.setValue(event.getNewValue());
        });
        descriptionColumn.setOnEditCommit(event -> {
            Header header = event.getRowValue();
            header.setDescription(event.getNewValue());
        });
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