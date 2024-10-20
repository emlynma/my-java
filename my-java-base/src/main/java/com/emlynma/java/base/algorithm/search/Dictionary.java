package com.emlynma.java.base.algorithm.search;

public interface Dictionary<Key extends Comparable<Key>, Value> {

    // 符号表（字典）

    void put(Key key, Value value);

    Value get(Key key);

    void delete(Key key);

    int size();

    default boolean contains(Key key) {
        return get(key) != null;
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    Iterable<Key> keys();

    // 有序符号表（有序字典）

    Key min();
    Key max();

    void deleteMin();
    void deleteMax();

    Key floor(Key key);    // 最大小于等于 key 的键
    Key ceiling(Key key);  // 最小大于等于 key 的键

    int rank(Key key);     // key 的排名（小于等于 key 的键的数量）
    Key select(int k);     // 排名第 k 的键

    int size(Key lo, Key hi);
    Iterable<Key> keys(Key lo, Key hi);

}
