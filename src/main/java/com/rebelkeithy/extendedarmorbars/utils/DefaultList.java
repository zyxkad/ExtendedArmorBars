package com.rebelkeithy.extendedarmorbars.utils;

import java.util.ArrayList;
import java.util.List;

public class DefaultList<T> extends ArrayList<T> {

    private final T internalDefault;

    public DefaultList(List<T> items, T internalDefault) {
        super(items);
        this.internalDefault = internalDefault;
    }

    public T get(int index) {
        if (index >= 0 && index < size()) {
            return super.get(index);
        }
        return internalDefault;
    }

    public T getOrDefault(int index, T defaultValue) {
        if (index >= 0 && index < size()) {
            return super.get(index);
        }
        return defaultValue;
    }
}
