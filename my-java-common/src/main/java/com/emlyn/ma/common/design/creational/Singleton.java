package com.emlyn.ma.common.design.creational;

public class Singleton {

    /**
     * 懒汉式，双重同步锁，线程安全
     */
    public static class SingletonV1 {
        private static volatile SingletonV1 instance;
        private SingletonV1() {}
        public static SingletonV1 getInstance() {
            if (instance == null) {
                synchronized (SingletonV1.class) {
                    if (instance == null) {
                        instance = new SingletonV1();
                    }
                }
            }
            return instance;
        }
    }

    /**
     * 懒汉式，内部类+常量，线程安全
     */
    public static class SingletonV2 {
        private SingletonV2() {}
        public static SingletonV2 getInstance() {
            return SingletonV2Holder.instance;
        }
        private static class SingletonV2Holder {
            private static final SingletonV2 instance = new SingletonV2();
        }
    }

    /**
     * 饿汉式，常量，天生线程安全
     */
    public static class SingletonV3 {
        private static final SingletonV3 instance = new SingletonV3();
        private SingletonV3() {}
        public static SingletonV3 getInstance() {
            return instance;
        }
    }

    /**
     * 饿汉式，枚举，天生线程安全
     */
    public enum SingletonV4 {
        INSTANCE
    }

}
