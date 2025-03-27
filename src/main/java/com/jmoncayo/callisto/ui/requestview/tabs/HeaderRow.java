package com.jmoncayo.callisto.ui.requestview.tabs;

import com.jmoncayo.callisto.requests.Header;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeaderRow {
	private final BooleanProperty enabled;
	public final SimpleStringProperty key;
	public final SimpleStringProperty value;
	public final SimpleStringProperty description;
	public boolean isPlaceholder;

	public HeaderRow(String key, String value, String description) {
		this.key = new SimpleStringProperty(key);
		this.value = new SimpleStringProperty(value);
		this.description = new SimpleStringProperty(description);
		this.enabled = new SimpleBooleanProperty();
	}

	public String getKey() {
		return key.get();
	}

	public String getValue() {
		return value.get();
	}

	public String getDescription() {
		return description.get();
	}

	public void setKey(String key) {
		this.key.set(key);
	}

	public void setValue(String value) {
		this.value.set(value);
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public void setPlaceholder(boolean placeholder) {
		isPlaceholder = placeholder;
	}

	public boolean isPlaceholder() {
		return isPlaceholder;
	}

	public BooleanProperty enabledProperty() {
		return enabled;
	}

	public static HeaderRow fromHeader(Header header) {
		var row = new HeaderRow(header.getKey(), header.getValue(), header.getDescription());
		row.setPlaceholder(false);
		return row;
	}
}
