package com.jmoncayo.callisto.ui.requestview.tabs;

import com.jmoncayo.callisto.ui.controllers.RequestController;
import com.jmoncayo.callisto.ui.customcomponents.KVDTableView;
import javafx.scene.layout.StackPane;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

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
}
