package com.jmoncayo.callisto.ui.save;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
class CollectionInfo {
	private final String name;
	private final String id;
	private List<CollectionInfo> subfolders;
	@Setter
	private boolean isSubFolder;

	public CollectionInfo(String name, String id) {
		this.name = name;
		this.id = id;
		this.subfolders = new ArrayList<>(5);
	}

	@Override
	public String toString() {
		return name;
	}

	public void addSubfolder(CollectionInfo subfolder) {
		subfolders.add(subfolder);
	}
}
