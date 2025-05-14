package com.jmoncayo.callisto.ui.sidenavigation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionTreeNode {
	private String displayName;
	private String requestId;
	private String collectionId;

	public CollectionTreeNode(String displayName, String collectionId, String requestId) {
		this.displayName = displayName;
		this.collectionId = collectionId;
		this.requestId = requestId;
	}

	public boolean isRequest() {
		return requestId != null;
	}


	@Override
	public String toString() {
		return displayName;
	}
}
