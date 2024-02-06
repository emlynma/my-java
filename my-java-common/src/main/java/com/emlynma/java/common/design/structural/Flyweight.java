package com.emlynma.java.common.design.structural;

import java.util.HashMap;
import java.util.Map;

public class Flyweight {

    // 享元接口
    interface Shape {
        void draw();
    }

    // 具体享元类
    static class Circle implements Shape {
        private final String color;

        public Circle(String color) {
            this.color = color;
        }

        @Override
        public void draw() {
            System.out.println("Drawing a circle with color: " + color);
        }
    }

    // 享元工厂类
    static class ShapeFactory {
        private final Map<String, Shape> shapeMap;

        public ShapeFactory() {
            shapeMap = new HashMap<>();
        }

        public Shape getCircle(String color) {
            if (shapeMap.containsKey(color)) {
                return shapeMap.get(color);
            } else {
                Shape circle = new Circle(color);
                shapeMap.put(color, circle);
                return circle;
            }
        }
    }

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        // 获取共享的红色圆形
        Shape redCircle = shapeFactory.getCircle("red");
        redCircle.draw();

        // 获取共享的蓝色圆形
        Shape blueCircle = shapeFactory.getCircle("blue");
        blueCircle.draw();

        // 获取共享的红色圆形（已存在，不需要创建新的对象）
        Shape redCircle2 = shapeFactory.getCircle("red");
        redCircle2.draw();
    }
}
