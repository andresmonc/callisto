package com.jmoncayo.callisto.storage;

import java.util.List;

public interface Loadable<T> {
    void load(List<T> data);
}
