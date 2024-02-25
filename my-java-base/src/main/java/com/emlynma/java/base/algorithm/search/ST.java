package com.emlynma.java.base.algorithm.search;

public interface ST<Key extends Comparable<Key>, Value> {

    void put(Key key, Value value);

    Value get(Key key);

    void delete(Key key);

    boolean contains(Key key);

    default boolean isEmpty() {
        return size() == 0;
    }

    int size();

    default int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        int size = rank(hi) - rank(lo);
        if (contains(hi)) {
            size = size + 1;
        }
        return size;
    }

    Key min();

    Key max();

    // 小于 key 的最大键
    Key floor(Key key);

    // 大于 key 的最小键
    Key ceiling(Key key);

    // 小于 key 的数量
    int rank(Key key);

    // 排名为 k 的键
    Key select(int k);

    default void deleteMin() {
        delete(min());
    }

    default void deleteMax() {
        delete(max());
    }

}
