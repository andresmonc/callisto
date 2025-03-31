package com.jmoncayo.callisto.ui.requestview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.ui.requestview.tabs.AuthorizationTab;
import com.jmoncayo.callisto.ui.requestview.tabs.HeadersTab;
import com.jmoncayo.callisto.ui.requestview.tabs.ParamsTab;
import com.jmoncayo.callisto.ui.requestview.tabs.SettingsTab;
import com.jmoncayo.callisto.ui.requestview.tabs.body.BodyTab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Getter
public class RequestDetails extends TabPane {
	private final HeadersTab headersTab;
	private final ParamsTab parametersTab;

	public RequestDetails(
			ParamsTab paramsTab,
			AuthorizationTab authorizationTab,
			HeadersTab headersTab,
			BodyTab bodyTab,
			SettingsTab settingsTab) {
		this.headersTab = headersTab;
		this.parametersTab = paramsTab;
		this.getTabs().addAll(paramsTab, authorizationTab, headersTab, bodyTab, settingsTab);
	}
}
