package com.jmoncayo.callisto.ui.sidenavigation;

public class CollectionTreeNode {
	private final String displayName;
	private final String requestId;
	private final String collectionId;

	public CollectionTreeNode(String displayName, String collectionId, String requestId) {
		this.displayName = displayName;
		this.collectionId = collectionId;
		this.requestId = requestId;
	}

	public boolean isRequest() {
		return requestId != null;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getCollectionId() {
		return collectionId;
	}

	public String getRequestId() {
		return requestId;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
