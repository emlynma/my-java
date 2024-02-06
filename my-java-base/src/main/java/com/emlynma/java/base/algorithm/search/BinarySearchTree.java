package com.emlynma.java.base.algorithm.search;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    public int size() {
        return size(root);
    }

    public Value get(Key key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    public Key min() {
        return min(root).key;
    }

    public Key max() {
        return max(root).key;
    }

    public Key select(int i) {
        return select(root, i).key;
    }

    private class Node {
        private final Key key;
        private Value value;
        private Node left, right;
        private int size;
        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    private Node get(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.right = put(node.right, key, value);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    private Node max(Node node) {
        if (node.right == null) {
            return node;
        }
        return min(node.right);
    }

    private Node select(Node node, int i) {
        if (node == null) {
            return null;
        }
        int t = size(node.left);
        if (i < t) {
            return select(node.left, i);
        } else if (i > t) {
            return select(node.right, i - t - 1);
        } else {
            return node;
        }
    }

}
