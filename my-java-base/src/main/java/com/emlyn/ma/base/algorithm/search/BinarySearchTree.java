package com.emlyn.ma.base.algorithm.search;

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

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.put(1, "a");
        bst.put(2, "b");
        bst.put(7, "c");
        bst.put(5, "d");
        bst.put(4, "e");
        bst.put(3, "f");
    }

}
