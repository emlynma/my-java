package com.emlynma.java.algo.search;

public class AVLTree<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int height;
        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            height = 0;
        }
    }

    // 获取节点的高度
    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    // 更新节点的高度
    private void updateHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // 获取节点的平衡因子
    private int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // 左旋转
    private Node leftRotate(Node node) {
        Node child = node.right;
        node.right = child.left;
        child.left = node;
        updateHeight(node);
        updateHeight(child);
        return child;
    }

    // 右旋转
    private Node rightRotate(Node node) {
        Node child = node.left;
        node.left = child.right;
        child.right = node;
        updateHeight(node);
        updateHeight(child);
        return child;
    }

    private Node rotate(Node node) {
        int balanceFactor = balanceFactor(node);
        // 左偏树
        if (balanceFactor > 1) {
            if (balanceFactor(node.left) >= 0) {
                // 右旋转
                return rightRotate(node);
            } else {
                // 先左旋转再右旋转
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // 右偏树
        if (balanceFactor < -1) {
            if (balanceFactor(node.right) <= 0) {
                // 左旋转
                return leftRotate(node);
            } else {
                // 先右旋转再左旋转
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        // 平衡树，无须旋转，直接返回
        return node;
    }

    public void put_recursion(Key key, Value value) {
        root = put_recursion_core(root, key, value);
    }
    private Node put_recursion_core(Node node, Key key, Value value) {
        // 递归出口
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = node.key.compareTo(key);
        if (cmp == 0) {
            node.value = value;
            return node;
        }
        if (cmp > 0) {
            node.left = put_recursion_core(node.left, key, value);
        } else {
            node.right = put_recursion_core(node.right, key, value);
        }
        // 更新高度
        updateHeight(node);
        // 旋转
        return rotate(node);
    }

}
