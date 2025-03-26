package com.jmoncayo.callisto.ui.requestview.tabs;

import javafx.beans.property.SimpleStringProperty;

public class HeaderRow {
    public final SimpleStringProperty key;
    public final SimpleStringProperty value;
    public final SimpleStringProperty description;
    public boolean isPlaceholder;

    public HeaderRow(String key, String value, String description) {
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleStringProperty(value);
        this.description = new SimpleStringProperty(description);
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
}
