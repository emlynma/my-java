package com.emlyn.ma.common.design.creational;

import lombok.Getter;

public class FactoryMethod {

    // 抽象产品类
    interface Product {
        void use();
        void setName(String name);
        void setPrice(double price);
    }

    // 抽象工厂类
    interface Factory {
        Product createProduct();
    }

    // 具体产品类A
    @Getter
    static class ProductA implements Product {
        private String name;
        private final String brand = "Apple";
        private double price;
        @Override
        public void use() {
            System.out.println("ProductA is used");
        }
        @Override
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public void setPrice(double price) {
            this.price = price;
        }
    }

    // 具体产品类B
    @Getter
    static class ProductB implements Product {
        private String name;
        private final String brand = "XiaoMi";
        private double price;
        @Override
        public void use() {
            System.out.println("ProductB is used");
        }
        @Override
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public void setPrice(double price) {
            this.price = price;
        }
    }

    // 具体工厂类A
    static class FactoryA implements Factory {
        @Override
        public Product createProduct() {
            ProductA productA = new ProductA();
            productA.setName("iPhone 13");
            productA.setPrice(7999.00);
            return productA;
        }
    }

    // 具体工厂类B
    static class FactoryB implements Factory {
        @Override
        public Product createProduct() {
            ProductB productB = new ProductB();
            productB.setName("Redmi 10");
            productB.setPrice(999.00);
            return productB;
        }
    }

    public static void main(String[] args) {
        Factory factoryA = new FactoryA();
        Product productA = factoryA.createProduct();
        productA.use();

        Factory factoryB = new FactoryB();
        Product productB = factoryB.createProduct();
        productB.use();
    }

}
