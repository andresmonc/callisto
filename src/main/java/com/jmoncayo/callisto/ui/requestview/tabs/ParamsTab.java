package com.jmoncayo.callisto.ui.requestview.tabs;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.scene.control.Tab;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Order(1)
public class ParamsTab extends Tab {
	public ParamsTab(ParamsTabView paramsTabView) {
		this.setText("Params");
		this.setClosable(false);
		this.setContent(paramsTabView);
	}
}
