package com.jmoncayo.callisto.ui.environmentview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.scene.control.Tab;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class EnvironmentTab extends Tab {

	public EnvironmentTab() {
		this.setClosable(true);
	}

	public void initNewEnvironment() {
		this.setText("New Environment");
	}
}
