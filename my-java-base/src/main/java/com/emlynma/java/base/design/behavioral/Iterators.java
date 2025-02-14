package com.emlynma.java.base.design.behavioral;

import java.util.ArrayList;
import java.util.List;

public class Iterators {

    // 迭代器接口
    interface Iterator<T> {
        boolean hasNext();
        T next();
    }

    // 容器接口
    interface Container {
        Iterator<String> createIterator();
    }

    // 具体迭代器
    static class ConcreteIterator implements Iterator<String> {
        private final List<String> elements;
        private int position = 0;
        public ConcreteIterator(List<String> elements) {
            this.elements = elements;
        }
        @Override
        public boolean hasNext() {
            return position < elements.size();
        }
        @Override
        public String next() {
            if (hasNext()) {
                String element = elements.get(position);
                position++;
                return element;
            }
            return null;
        }
    }

    // 具体容器
    static class ConcreteContainer implements Container {
        private final List<String> elements;
        public ConcreteContainer() {
            elements = new ArrayList<>();
        }
        public void addElement(String element) {
            elements.add(element);
        }
        @Override
        public Iterator<String> createIterator() {
            return new ConcreteIterator(elements);
        }
    }

    public static void main(String[] args) {
        ConcreteContainer container = new ConcreteContainer();
        container.addElement("Element 1");
        container.addElement("Element 2");
        container.addElement("Element 3");

        Iterator<String> iterator = container.createIterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println("Element: " + element);
        }
    }

}
