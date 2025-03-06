package com.emlynma.java.base.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<Key, Value> extends LinkedHashMap<Key, Value> {

    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Key, Value> eldest) {
        return size() > capacity;
    }

}
