package com.emlynma.java.algo.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 二叉搜索树 迭代实现
 */
public class BinarySearchTree2<Key extends Comparable<Key>, Value> implements BinaryTree<Key, Value> {

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
        return postList().stream().map(node -> node.value).toList();
    }

    @Override
    public Value get(Key key) {
        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                break;
            }
            if (cmp < 0) {
                node = node.left;
            }
            if (cmp > 0) {
                node = node.right;
            }
        }
        return node == null ? null : node.value;
    }

    @Override
    public void put(Key key, Value value) {
        // 根节点为空，直接赋值即可
        if (root == null) {
            root = new Node(key, value);
            return;
        }
        // 根节点不为空，遍历查找插入点
        Node node = root, prev = null;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                // 已存在节点，直接更新
                node.value = value;
                return;
            } else if (cmp < 0) {
                prev = node;
                node = node.left;
            } else {
                prev = node;
                node = node.right;
            }
        }
        // 找到插入点，新节点应该插入到prev下边
        if (key.compareTo(prev.key) < 0) {
            prev.left = new Node(key, value);
        } else {
            prev.right = new Node(key, value);
        }
    }

    @Override
    public void del(Key key) {
        if (root == null) {
            return;
        }
        Node node = root, prev = null;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                break;
            } else if (cmp < 0) {
                prev = node;
                node = node.left;
            } else {
                prev = node;
                node = node.right;
            }
        }
        // 找不到目标，返回
        if (node == null) {
            return;
        }
        Node child;
        if (node.left == null || node.right == null) {
            child = node.left != null ? node.left : node.right;
        } else {
            child = node.right;
            Node prev2 = node;
            while (child.left != null) {
                prev2 = child;
                child = child.left;
            }
            if (prev2.left == child) {
                prev2.left = child.right;
            } else {
                prev2.right = child.right;
            }
            // 替换
            child.left = node.left;
            child.right = node.right;
        }
        if (node == root) {
            root = child;
        } else {
            if (prev.left == node) {
                prev.left = child;
            } else {
                prev.right = child;
            }
        }
    }

    // 遍历
    // 前序遍历
    private List<Node> preList() {
        List<Node> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            result.add(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return result;
    }
    // 中序遍历
    private List<Node> inList() {
        List<Node> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (!stack.isEmpty() || node != null) {
            // 把左子树入栈
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            list.add(node);
            node = node.right;
        }
        return list;
    }
    // 后序遍历
    private List<Node> postList() {
        List<Node> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            result.add(node);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        // 结果反转
        Collections.reverse(result);
        return result;
    }
}
