package com.emlynma.java.algo.search;

import java.util.List;

public class ReadBlackTree<Key extends Comparable<Key>, Value> implements BinaryTree<Key, Value> {

    private enum Color {
        RED, BLACK
    }

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private Color color;
        public Node(Key key, Value value, Color color) {}
    }

    private Node root;

    @Override
    public List<Value> list() {
        // 参考二叉搜索树
        return null;
    }

    @Override
    public Value get(Key key) {
        // 参考二叉搜索树
        return null;
    }

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    @Override
    public void del(Key key) {

    }

    // 添加
    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, Color.RED);
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        }
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        }
        if (cmp > 0) {
            node.right = put(node.right, key, value);
        }
        // Red Black 特殊操作
        {
            if (isRed(node.right) && !isRed(node.left)) {
                node = leftRotate(node);
            }
            if (isRed(node.left) && isRed(node.left.left)) {
                node = rightRotate(node);
            }
            if (isRed(node.left) && isRed(node.right)) {
                flipColor(node);
            }
        }
        return node;
    }

    // RedBlackTree 特定辅助函数
    // 是否是红节点
    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color == Color.RED;
    }
    // 左旋
    private Node leftRotate(Node node) {
        Node child = node.right;
        node.right = child.left;
        child.left = node;
        child.color = node.color;
        node.color = Color.RED;
        return child;
    }
    // 右旋
    private Node rightRotate(Node node) {
        Node child = node.left;
        node.left = child.right;
        child.right = node;
        child.color = node.color;
        node.color = Color.RED;
        return child;
    }
    // 颜色变换
    private void flipColor(Node node) {
        node.color = Color.RED;
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
    }
}
