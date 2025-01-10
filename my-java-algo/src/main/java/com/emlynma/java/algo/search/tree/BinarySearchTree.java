package com.emlynma.java.algo.search.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTree<Key extends Comparable<Key>, Value> implements BinaryTree<Key, Value> {

    private class Node {
        Key key;
        Value value;
        Node left, right;
        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;

    @Override
    public List<Value> list() {
        return preList().stream().map(node -> node.value).toList();
    }

    @Override
    public Value get(Key key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    @Override
    public void del(Key key) {
        root = del(root, key);
    }

    // 遍历
    // 层序遍历
    private List<Node> levelList() {
        List<Node> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            result.add(node);
            if (node.right != null) {
                queue.add(node.right);
            }
            if (node.left != null) {
                queue.add(node.left);
            }
        }
        return result;
    }
    // 前序遍历
    private List<Node> preList() {
        List<Node> result = new ArrayList<>();
        preListCore(root, result);
        return result;
    }
    private void preListCore(Node node, List<Node> result) {
        if (node == null) {
            return;
        }
        result.add(node);
        preListCore(node.left, result);
        preListCore(node.right, result);
    }
    // 中序遍历
    private List<Node> inList() {
        List<Node> result = new ArrayList<>();
        inListCore(root, result);
        return result;
    }
    private void inListCore(Node node, List<Node> result) {
        if (node == null) {
            return;
        }
        inListCore(node.left, result);
        result.add(node);
        inListCore(node.right, result);
    }
    // 后序遍历
    private List<Node> postList() {
        List<Node> result = new ArrayList<>();
        postListCore(root, result);
        return result;
    }
    private void postListCore(Node node, List<Node> result) {
        if (node == null) {
            return;
        }
        postListCore(node.left, result);
        postListCore(node.right, result);
        result.add(node);
    }

    // 查找
    private Node get(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
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
        return node;
    }
}
