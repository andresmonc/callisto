package com.jmoncayo.callisto.ui.requestview.tabs;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.scene.control.Tab;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Order(1)
@Getter
public class ParamsTab extends Tab {
	private final ParamsTabView paramsTabView;

	public ParamsTab(ParamsTabView paramsTabView) {
		this.paramsTabView = paramsTabView;
		this.setText("Params");
		this.setClosable(false);
		this.setContent(paramsTabView);
	}
}
