package com.jmoncayo.callisto.ui.requestview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.ui.requestview.tabs.HeadersTab;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestDetails extends TabPane {
	@Getter
	private final HeadersTab headersTab;

	public RequestDetails(List<Tab> tabs, HeadersTab headersTab) {
		this.headersTab = headersTab;
		this.getTabs().addAll(tabs);
	}
}
