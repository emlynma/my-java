package com.emlynma.java.algo.search;

import java.util.*;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private final Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;
        private Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    // 查找
    public Value get(Key key) {
        // 迭代实现
        Node node = this.get_iteration(key);
        // 递归实现
        // Node node = this.get_recursive(key);

        return node == null ? null : node.value;
    }
    // 查找-迭代
    private Node get_iteration(Key key) {
        Node node = this.root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                break;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }
    // 查找-递归
    private Node get_recursive(Key key) {
        return get_recursive_core(this.root, key);
    }
    private Node get_recursive_core(Node node, Key key) {
        // 递归出口
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return get_recursive_core(node.left, key);
        } else {
            return get_recursive_core(node.right, key);
        }
    }

    // 添加
    public void put(Key key, Value value) {
        // 迭代实现
        put_iteration(key, value);
        // 递归实现
        // put_recursion(key, value);
    }
    // 添加-迭代
    private void put_iteration(Key key, Value value) {
        // 根节点为空，直接赋值即可
        if (root == null) {
            root = new Node(key, value, 1);
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
            prev.left = new Node(key, value, 1);
        } else {
            prev.right = new Node(key, value, 1);
        }
        // 刷新size
        refresh_size(root);
    }
    // 添加-递归
    private void put_recursion(Key key, Value value) {
        root = put_recursion_core(root, key, value);
    }
    private Node put_recursion_core(Node node, Key key, Value value) {
        // 递归出口
        if (node == null) {
            return new Node(key, value, 1);
        }
        int cmp = node.key.compareTo(key);
        if (cmp == 0) {
            node.value = value;
        }
        // 更新参数，调用自身
        if (cmp > 0) {
            node.left = put_recursion_core(node.left, key, value);
        } else {
            node.right = put_recursion_core(node.right, key, value);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    // 删除
    public void delete(Key key) {
        // 迭代实现
        // delete_iteration(key);
        // 递归实现
        delete_recursion(key);
    }
    // 删除-迭代
    private void delete_iteration(Key key) {
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

        refresh_size(root);
    }
    // 删除-递归
    private void delete_recursion(Key key) {
        root = delete_recursion_core(root, key);
    }
    private Node delete_recursion_core(Node node, Key key) {
        // 递归出口
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            if (node.left == null || node.right == null) {
                return node.left != null ? node.left : node.right;
            } else {
                Node temp = node.right;
                Node prev = node;
                while (temp.left != null) {
                    prev = temp;
                    temp = temp.left;
                }
                // 删除temp
                if (prev.left == temp) {
                    prev.left = delete_recursion_core(temp, temp.key);
                } else {
                    prev.right = delete_recursion_core(temp, temp.key);
                }
                temp.left = node.left;
                temp.right = node.right;
                return temp;
            }
        }
        if (cmp < 0) {
            node.left = delete_recursion_core(node.left, key);
        } else {
            node.right = delete_recursion_core(node.right, key);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    // 遍历
    public List<Value> list() {
        List<Node> list;
        // 层序遍历
        list = level_list();
        // 前序遍历
        list = pre_list_recursive();
        // 中序遍历
        list = in_list_recursive();
        // 后序遍历
        list = post_list_recursive();

        return list.stream().map(i -> i.value).toList();
    }
    // 层序遍历
    private List<Node> level_list() {
        List<Node> list = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            list.add(node);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return list;
    }
    private List<Node> list;
    // 前序遍历
    private List<Node> pre_list_iteration() {
        List<Node> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            list.add(node);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return list;
    }
    private List<Node> pre_list_recursive() {
        list = new ArrayList<>();
        pre_list_recursive_core(root);
        return list;
    }
    private void pre_list_recursive_core(Node node) {
        if (node == null) {
            return;
        }
        list.add(node);
        pre_list_recursive_core(node.left);
        pre_list_recursive_core(node.right);
    }
    // 中序遍历
    private List<Node> in_list_iteration() {
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
    private List<Node> in_list_recursive() {
        list = new ArrayList<>();
        in_list_recursive_core(root);
        return list;
    }
    private void in_list_recursive_core(Node node) {
        if (node == null) {
            return;
        }
        in_list_recursive_core(node.left);
        list.add(node);
        in_list_recursive_core(node.right);
    }
    // 后序遍历
    private List<Node> post_list_iteration() {
        List<Node> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Stack<Node> resultStack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            resultStack.push(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        while (!resultStack.isEmpty()) {
            list.add(resultStack.pop());
        }
        return list;
    }
    private List<Node> post_list_recursive() {
        list = new ArrayList<>();
        post_list_recursive_core(root);
        return list;
    }
    private void post_list_recursive_core(Node node) {
        if (node == null) {
            return;
        }
        post_list_recursive_core(node.left);
        post_list_recursive_core(node.right);
        list.add(node);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    private void refresh_size(Node node) {
        if (node == null) {
            return;
        }
        refresh_size(node.left);
        refresh_size(node.right);
        node.size = size(node.left) + size(node.right) + 1;
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.put(3, 3);
        tree.put(2, 2);
        tree.put(4, 4);
        tree.put(1, 1);
        tree.put(5, 5);
        tree.pre_list_iteration().stream().map(i -> i.value).forEach(System.out::print);
        System.out.println();
        tree.delete_iteration(3);
        tree.pre_list_recursive().stream().map(i -> i.value).forEach(System.out::print);
    }
}
