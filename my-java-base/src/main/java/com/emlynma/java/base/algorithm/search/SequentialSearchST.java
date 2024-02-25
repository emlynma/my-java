package com.emlynma.java.base.algorithm.search;

import lombok.AllArgsConstructor;

public class SequentialSearchST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

    private Node first;

    @AllArgsConstructor
    private class Node {
        Key key;
        Value value;
        Node next;
    }

    @Override
    public Value get(Key key) {
        Node node = this.getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private Node getNode(Key key) {
        for (var node = first; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void put(Key key, Value value) {
        Node node = this.getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }
        first = new Node(key, value, first);
    }

    @Override
    public void delete(Key key) {
        first = delete(first, key);
    }

    // 递归函数
    private Node delete(Node node, Key key) {
        // 递归边界
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            return node.next;
        }
        // 递推公式（修改参数，再次调用）
        node.next = delete(node.next, key);
        return node;
    }

    @Override
    public boolean contains(Key key) {
        return getNode(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() {
        int size = 0;
        for (var node = first; node != null; node = node.next) {
            size++;
        }
        return size;
    }

    @Override
    public Key min() {
        Key min = null;
        for (var node = first; node != null; node = node.next) {
            if (min == null || node.key.compareTo(min) < 0) {
                min = node.key;
            }
        }
        return min;
    }

    @Override
    public Key max() {
        Key max = null;
        for (var node = first; node != null; node = node.next) {
            if (max == null || node.key.compareTo(max) > 0) {
                max = node.key;
            }
        }
        return max;
    }

    @Override
    public Key floor(Key key) {
        Key floor = null;
        for (var node = first; node != null; node = node.next) {
            if (node.key.compareTo(key) <= 0) {
                if (floor == null || node.key.compareTo(floor) > 0) {
                    floor = node.key;
                }
            }
        }
        return floor;
    }

    @Override
    public Key ceiling(Key key) {
        Key ceiling = null;
        for (var node = first; node != null; node = node.next) {
            if (node.key.compareTo(key) >= 0) {
                if (ceiling == null || node.key.compareTo(ceiling) < 0) {
                    ceiling = node.key;
                }
            }
        }
        return ceiling;
    }

    @Override
    public int rank(Key key) {
        int rank = 0;
        for (var node = first; node != null; node = node.next) {
            if (node.key.compareTo(key) < 0) {
                rank++;
            }
        }
        return rank;
    }

    @Override
    public Key select(int k) {
        for (var node = first; node != null; node = node.next) {
            if (rank(node.key) == k) {
                return node.key;
            }
        }
        return null;
    }
}
