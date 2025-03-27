package com.jmoncayo.callisto.ui.requestview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.ui.requestview.tabs.AuthorizationTab;
import com.jmoncayo.callisto.ui.requestview.tabs.BodyTab;
import com.jmoncayo.callisto.ui.requestview.tabs.HeadersTab;
import com.jmoncayo.callisto.ui.requestview.tabs.ParamsTab;
import com.jmoncayo.callisto.ui.requestview.tabs.SettingsTab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestDetails extends TabPane {
	@Getter
	private final HeadersTab headersTab;

	public RequestDetails(
			ParamsTab paramsTab,
			AuthorizationTab authorizationTab,
			HeadersTab headersTab,
			BodyTab bodyTab,
			SettingsTab settingsTab) {
		this.headersTab = headersTab;
		this.getTabs().addAll(paramsTab, authorizationTab, headersTab, bodyTab, settingsTab);
	}
}
