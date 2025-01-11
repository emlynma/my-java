package com.emlynma.java.algo.search;

import java.util.List;

public class AVLTree<Key extends Comparable<Key>, Value> implements BinaryTree<Key, Value> {

    private class Node {
        Key key;
        Value value;
        Node left, right;
        int height;
        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.height = 0;
        }
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
        root = del(root, key);
    }

    // 添加
    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
            return node;
        }
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.right = put(node.right, key, value);
        }
        // AVL 特殊操作
        {
            // 更新高度
            updateHeight(node);
            // 旋转
            node = rotate(node);
        }
        return node;
    }

    // 删除
    private Node del(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // 如果待删除节点有两个子节点，返回右节点的最小节点
            Node child = node.right;
            while (child.left != null) {
                child = child.left;
            }
            child = del(child, key);
            child.left = node.left;
            child.right = node.right;
            return child;
        }
        if (cmp < 0) {
            node.left = del(node.left, key);
        } else {
            node.right = del(node.right, key);
        }
        // AVL 特殊操作
        {
            updateHeight(node);
            node = rotate(node);
        }
        return node;
    }

    // AVL 辅助函数
    // 节点高度
    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }
    // 更新高度
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }
    // 节点平衡因子
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
    // 旋转
    private Node rotate(Node node) {
        int balanceFactor = balanceFactor(node);
        // 右偏树，需要左旋
        if (balanceFactor < -1) {
            if (balanceFactor(node.right) <= 0) {
                // 左旋
                return leftRotate(node);
            } else {
                // 先右旋，再左旋
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        // 左偏树，需要右旋
        if (balanceFactor > 1) {
            if (balanceFactor(node.left) >= 0) {
                // 右旋
                return rightRotate(node);
            } else {
                // 先左旋，再右旋
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        return node;
    }
}
