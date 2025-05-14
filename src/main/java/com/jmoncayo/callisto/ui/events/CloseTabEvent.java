package com.jmoncayo.callisto.ui.events;

import javafx.scene.control.Tab;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CloseTabEvent extends ApplicationEvent {

	private final String id;
	private final TabType tabType;
	private final Tab tab;

	public CloseTabEvent(Object source, String id, TabType tabType, Tab tab) {
		super(source);
		this.id = id;
		this.tabType = tabType;
		this.tab = tab;
	}

	public enum TabType {
		ENVIRONMENT,
		REQUEST,
		COLLECTION
	}
}
