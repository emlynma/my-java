package com.emlynma.java.base.algorithm.search;

import lombok.AllArgsConstructor;

import java.util.Iterator;

/**
 * 基于无序链表实现的字典
 */
public class LinkedDictionary<Key extends Comparable<Key>, Value> implements Dictionary<Key, Value> {

    private int size;

    @AllArgsConstructor
    private class Node {
        Key key;
        Value value;
        Node next;
    }

    private Node head;

    @Override
    public void put(Key key, Value value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
        } else {
            head = new Node(key, value, head);
        }
        size++;
    }

    @Override
    public Value get(Key key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public void delete(Key key) {
        for (Node prev = null, node = head; node != null; prev = node, node = node.next) {
            if (key.equals(node.key)) {
                if (node == head) {
                    head = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        return () -> new Iterator<>() {
            private Node node = head;
            @Override
            public boolean hasNext() {
                return node != null;
            }
            @Override
            public Key next() {
                var key = node.key;
                node = node.next;
                return key;
            }
        };
    }

    @Override
    public Key min() {
        Key min = null;
        for (var node = head; node != null; node = node.next) {
            if (min == null || node.key.compareTo(min) < 0) {
                min = node.key;
            }
        }
        return min;
    }

    @Override
    public Key max() {
        Key max = null;
        for (var node = head; node != null; node = node.next) {
            if (max == null || node.key.compareTo(max) > 0) {
                max = node.key;
            }
        }
        return max;
    }

    @Override
    public void deleteMin() {
        delete(min());
    }

    @Override
    public void deleteMax() {
        delete(max());
    }

    @Override
    public Key floor(Key key) {
        Key floor = null;
        for (var node = head; node != null; node = node.next) {
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
        for (var node = head; node != null; node = node.next) {
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
        var rank = 0;
        for (var node = head; node != null; node = node.next) {
            if (node.key.compareTo(key) < 0) {
                rank++;
            }
        }
        return rank;
    }

    @Override
    public Key select(int k) {
        if (k >= size) {
            return null;
        }
        for (var node = head; node != null; node = node.next) {
            if (rank(node.key) == k) {
                return node.key;
            }
        }
        return null;
    }

    @Override
    public int size(Key lo, Key hi) {
        int size = 0;
        for (var node = head; node != null; node = node.next) {
            if (node.key.compareTo(lo) >= 0 && node.key.compareTo(hi) <= 0) {
                size++;
            }
        }
        return size;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return () -> new Iterator<>() {
            private Node node = head;
            private int remain = size(lo, hi);
            @Override
            public boolean hasNext() {
                return head != null && remain > 0;
            }
            @Override
            public Key next() {
                for (; head != null; node = node.next) {
                    if (node.key.compareTo(lo) >= 0 && node.key.compareTo(hi) <= 0) {
                        var key = node.key;
                        node = node.next;
                        remain--;
                        return key;
                    }
                }
                return null;
            }
        };
    }

    private Node getNode(Key key) {
        if (isEmpty()) {
            return null;
        }
        for (var node = head; node != null; node = node.next) {
            if (key.equals(node.key)) {
                return node;
            }
        }
        return null;
    }

}
