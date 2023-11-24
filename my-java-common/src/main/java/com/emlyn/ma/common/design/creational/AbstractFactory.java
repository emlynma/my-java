package com.emlyn.ma.common.design.creational;

public class AbstractFactory {

    // 抽象产品A
    interface ProductA {
        void use();
    }

    // 抽象产品B
    interface ProductB {
        void use();
    }

    // 具体产品A1
    static class ProductA1 implements ProductA {
        @Override
        public void use() {
            System.out.println("ProductA1 is used");
        }
    }

    // 具体产品A2
    static class ProductA2 implements ProductA {
        @Override
        public void use() {
            System.out.println("ProductA2 is used");
        }
    }

    // 具体产品B1
    static class ProductB1 implements ProductB {
        @Override
        public void use() {
            System.out.println("ProductB1 is used");
        }
    }

    // 具体产品B2
    static class ProductB2 implements ProductB {
        @Override
        public void use() {
            System.out.println("ProductB2 is used");
        }
    }

    // 抽象工厂
    interface Factory {
        ProductA createProductA();
        ProductB createProductB();
    }

    // 具体工厂1
    static class Factory1 implements Factory {
        @Override
        public ProductA createProductA() {
            return new ProductA1();
        }
        @Override
        public ProductB createProductB() {
            return new ProductB1();
        }
    }

    // 具体工厂2
    static class Factory2 implements Factory {
        @Override
        public ProductA createProductA() {
            return new ProductA2();
        }
        @Override
        public ProductB createProductB() {
            return new ProductB2();
        }
    }

    public static void main(String[] args) {
        Factory factory1 = new Factory1();
        ProductA productA1 = factory1.createProductA();
        ProductB productB1 = factory1.createProductB();
        productA1.use();
        productB1.use();

        Factory factory2 = new Factory2();
        ProductA productA2 = factory2.createProductA();
        ProductB productB2 = factory2.createProductB();
        productA2.use();
        productB2.use();
    }

}
