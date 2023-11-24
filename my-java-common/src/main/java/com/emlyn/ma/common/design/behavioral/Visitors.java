package com.emlyn.ma.common.design.behavioral;

import java.util.ArrayList;
import java.util.List;

public class Visitors {

    // 抽象元素
    interface Element {
        void accept(Visitor visitor);
    }

    // 具体元素
    static class ConcreteElementA implements Element {
        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public void operationA() {
            System.out.println("Operation A");
        }
    }

    static class ConcreteElementB implements Element {
        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public void operationB() {
            System.out.println("Operation B");
        }
    }

    // 抽象访问者
    interface Visitor {
        void visit(ConcreteElementA elementA);
        void visit(ConcreteElementB elementB);
    }

    // 具体访问者
    static class ConcreteVisitor implements Visitor {
        @Override
        public void visit(ConcreteElementA elementA) {
            elementA.operationA();
            System.out.println("Visitor visits ConcreteElementA");
        }
        @Override
        public void visit(ConcreteElementB elementB) {
            elementB.operationB();
            System.out.println("Visitor visits ConcreteElementB");
        }
    }

    // 对象结构
    static class ObjectStructure {
        private List<Element> elements = new ArrayList<>();

        public void addElement(Element element) {
            elements.add(element);
        }

        public void accept(Visitor visitor) {
            for (Element element : elements) {
                element.accept(visitor);
            }
        }
    }

    public static void main(String[] args) {
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.addElement(new ConcreteElementA());
        objectStructure.addElement(new ConcreteElementB());

        Visitor visitor = new ConcreteVisitor();
        objectStructure.accept(visitor);
    }

}
