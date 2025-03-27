package com.jmoncayo.callisto.ui.requestview.tabs;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.ui.controllers.RequestController;
import com.jmoncayo.callisto.ui.customcomponents.KVDTableView;
import javafx.scene.layout.StackPane;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Log4j2
public class ParamsTabView extends StackPane {

	private final RequestController requestController;
	private final KVDTableView tableView;

	public ParamsTabView(RequestController requestController, KVDTableView kvdTableView) {
		this.requestController = requestController;
		this.tableView = kvdTableView;
		getChildren().add(kvdTableView);
	}
}
