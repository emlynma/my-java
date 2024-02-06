package com.emlynma.java.common.design.structural;

public class Bridge {

    // 抽象部分
    static abstract class Shape {
        protected Color color;

        public Shape(Color color) {
            this.color = color;
        }

        public abstract void applyColor();
    }

    // 具体抽象部分
    static class Square extends Shape {
        public Square(Color color) {
            super(color);
        }

        @Override
        public void applyColor() {
            System.out.print("Square filled with ");
            color.applyColor();
        }
    }

    // 实现部分
    interface Color {
        void applyColor();
    }

    // 具体实现部分
    static class Red implements Color {
        @Override
        public void applyColor() {
            System.out.println("Red color");
        }
    }
    static class Blue implements Color {
        @Override
        public void applyColor() {
            System.out.println("Blue color");
        }
    }

    // 客户端代码
    public static void main(String[] args) {
        Color red = new Red();
        Shape square = new Square(red);
        square.applyColor();

        Color blue = new Blue();
        Shape square2 = new Square(blue);
        square2.applyColor();
    }
}
