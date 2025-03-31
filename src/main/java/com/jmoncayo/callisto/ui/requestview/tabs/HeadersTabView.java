package com.jmoncayo.callisto.ui.requestview.tabs;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.requests.ApiRequest;
import com.jmoncayo.callisto.requests.Header;
import com.jmoncayo.callisto.ui.controllers.RequestController;
import com.jmoncayo.callisto.ui.customcomponents.KVDTableView;
import com.jmoncayo.callisto.ui.customcomponents.TableEntry;
import java.util.Optional;
import javafx.scene.layout.StackPane;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Log4j2
public class HeadersTabView extends StackPane {
	private final RequestController requestController;
	private final KVDTableView tableView;

	public HeadersTabView(RequestController requestController, KVDTableView kvdTableView) {
		this.requestController = requestController;
		this.tableView = kvdTableView;
		getChildren().add(kvdTableView);
	}

	public void initialize(ApiRequest request) {
		if (request.getHeaders() != null) {
			for (Header header : request.getHeaders()) {
				TableEntry tableEntry = TableEntry.fromKeyValueDescription(header);
				tableView.getItems().add(tableEntry);
			}
		}
		Optional<TableEntry> placeholderRow = tableView.getItems().stream()
				.filter(tableEntry -> tableEntry.getPlaceholder().get())
				.findAny();
		if (placeholderRow.isEmpty()) {
			TableEntry newPlaceholderRow = new TableEntry(true, "Key", "Value", "Description", false);
			tableView.getItems().add(newPlaceholderRow);
		}
		tableView.getItems().addListener((javafx.collections.ListChangeListener<TableEntry>)
				change -> requestController.updateAllHeaders(tableView.getItems()));
		tableView.getItems().forEach(tableEntry -> {
			tableEntry
					.getPlaceholder()
					.addListener((ob, o, n) -> requestController.updateAllHeaders(tableView.getItems()));
			tableEntry.getEnabled().addListener((ob, o, n) -> requestController.updateAllHeaders(tableView.getItems()));
			tableEntry.getValue().addListener((ob, o, n) -> requestController.updateAllHeaders(tableView.getItems()));
			tableEntry.getKey().addListener((ob, o, n) -> requestController.updateAllHeaders(tableView.getItems()));
			tableEntry
					.getDescription()
					.addListener((ob, o, n) -> requestController.updateAllHeaders(tableView.getItems()));
		});
	}
}
