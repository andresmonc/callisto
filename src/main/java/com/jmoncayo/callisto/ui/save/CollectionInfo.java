package com.jmoncayo.callisto.ui.save;

import lombok.Getter;

import java.util.Objects;

@Getter
class CollectionInfo {
    private final String name;
    private final String id;

    public CollectionInfo(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CollectionInfo) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

}