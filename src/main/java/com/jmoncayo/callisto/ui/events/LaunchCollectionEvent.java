package com.jmoncayo.callisto.ui.events;

import com.jmoncayo.callisto.ui.sidenavigation.SideNavigationCollectionTreeView;
import org.springframework.context.ApplicationEvent;

public class LaunchCollectionEvent extends ApplicationEvent {
	public LaunchCollectionEvent(
			SideNavigationCollectionTreeView sideNavigationCollectionTreeView, String collectionId) {
		super(sideNavigationCollectionTreeView);
	}
}
