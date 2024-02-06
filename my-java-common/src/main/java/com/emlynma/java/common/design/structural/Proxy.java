package com.emlynma.java.common.design.structural;

import java.lang.reflect.InvocationHandler;

public class Proxy {

    public interface Image {
        void display();
    }

    static class RealImage implements Image {
        private final String filename;

        public RealImage(String filename) {
            this.filename = filename;
            loadFromDisk();
        }

        private void loadFromDisk() {
            System.out.println("Loading image: " + filename);
        }

        @Override
        public void display() {
            System.out.println("Displaying image: " + filename);
        }
    }

    static class ImageProxy implements Image {
        private final String filename;
        private RealImage realImage;

        public ImageProxy(String filename) {
            this.filename = filename;
        }

        @Override
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(filename);
            }
            realImage.display();
        }
    }

    // 代理模式充当了图像对象的控制代理，实现了延迟加载和缓存的功能
    public static void main(String[] args) {
        Image image1 = new ImageProxy("image1.jpg");
        Image image2 = new ImageProxy("image2.jpg");

        // 图像对象在调用 display() 之前不会被实际加载
        image1.display();

        // 图像对象在第二次调用 display() 时已经加载过了，不需要再次加载
        image1.display();

        // 图像对象在调用 display() 之前不会被实际加载
        image2.display();

        System.out.println("---------------------------------------");

        // 动态代理
        Image image3 = new RealImage("image3.jpg");
        InvocationHandler handler = (proxy, method, args1) -> {
            if (method.getName().equals("display")) {
                System.out.println("Before display " + method.getName());
                Object result = method.invoke(image3, args1);
                System.out.println("After display " + method.getName());
                return result;
            } else {
                return method.invoke(image3, args1);
            }
        };
        Image proxyImage3 = (Image) java.lang.reflect.Proxy.newProxyInstance(
                Image.class.getClassLoader(),
                new Class[]{Image.class},
                handler
        );
        proxyImage3.display();
    }
}
