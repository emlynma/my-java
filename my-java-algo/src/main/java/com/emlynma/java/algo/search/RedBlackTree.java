package com.emlynma.java.algo.search;

public class RedBlackTree<Key extends Comparable<Key>, Value> {

    private enum Color {
        BLACK, RED
    }

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private Color color;
        public Node(Key key, Value value, Color color) {}
        public boolean isRed() {
            return color == Color.RED;
        }
    }



}
