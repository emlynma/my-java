package com.emlynma.java.algo.search;

import java.util.List;

public interface BinaryTree<Key extends Comparable<Key>, Value> {

    // 遍历
    List<Value> list();

    // 查找
    Value get(Key key);

    // 插入
    void put(Key key, Value value);

    // 删除
    void del(Key key);

}
