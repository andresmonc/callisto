package com.jmoncayo.callisto.ui.customcomponents;

import com.jmoncayo.callisto.requests.KeyValueDescription;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class TableEntry {
	private final BooleanProperty enabled = new SimpleBooleanProperty();
	private final BooleanProperty placeholder = new SimpleBooleanProperty();
	private final StringProperty key = new SimpleStringProperty();
	private final StringProperty value = new SimpleStringProperty();
	private final StringProperty description = new SimpleStringProperty();

	public TableEntry(boolean enabled, String key, String value, String description, boolean placeholder) {
		this.enabled.set(enabled);
		this.key.set(key);
		this.value.set(value);
		this.description.set(description);
		this.placeholder.set(placeholder);
	}

	public BooleanProperty enabledProperty() {
		return enabled;
	}

	public StringProperty keyProperty() {
		return key;
	}

	public StringProperty valueProperty() {
		return value;
	}

	public StringProperty descriptionProperty() {
		return description;
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

	public static TableEntry fromKeyValueDescription(KeyValueDescription keyValueDescription) {
		return new TableEntry(
				keyValueDescription.isEnabled(),
				keyValueDescription.getKey(),
				keyValueDescription.getValue(),
				keyValueDescription.getDescription(),
				keyValueDescription.isPlaceholder());
	}
}
