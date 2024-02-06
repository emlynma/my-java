package com.emlynma.java.common.design.creational;

public class SimpleFactory {

    // 抽象产品类
    interface Product {
        void use();
        void setName(String name);
        void setPrice(double price);
    }

    // 具体产品类A
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

    // 简单工厂类
    static class ProductFactory {
        public static Product createProduct(String type) {
            if ("A".equals(type)) {
                ProductA productA = new ProductA();
                productA.setName("iPhone");
                productA.setPrice(9999.99);
                return productA;
            } else if ("B".equals(type)) {
                ProductB productB = new ProductB();
                productB.setName("RedMi");
                productB.setPrice(1999.99);
                return productB;
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        Product productA = ProductFactory.createProduct("A");
        productA.use();

        Product productB = ProductFactory.createProduct("B");
        productB.use();
    }

}
