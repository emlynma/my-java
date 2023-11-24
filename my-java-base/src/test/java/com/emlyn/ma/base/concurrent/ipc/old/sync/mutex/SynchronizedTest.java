package com.emlyn.ma.base.concurrent.ipc.old.sync.mutex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 互斥同步（阻塞同步）之Synchronized
 */
public class SynchronizedTest {

    static class Counter {
        private int count = 0;
        public int getCount() {
            return this.count;
        }
        public void increase(int n) {
            this.count += n;
        }
        public void reduce(int n) {
            this.count -= n;
        }
        public synchronized void syncIncrease(int n) {
            this.count += n;
        }
        public synchronized void syncReduce(int n) {
            this.count -= n;
        }
    }

    @Test // 使用同步方法
    public void testSynchronizedMethod() throws InterruptedException {
        Counter counter = new Counter();
        Thread a = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.syncIncrease(1);
            }
        });
        Thread b = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.syncIncrease(1);
            }
        });
        Thread c = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.syncReduce(1);
            }
        });
        a.start(); b.start(); c.start();
        a.join(); b.join(); c.join();
        Assertions.assertEquals(10000, counter.getCount());
    }

    @Test // 使用同步块
    public void testSynchronizedBlock() throws InterruptedException {
        Counter counter = new Counter();
        Object lock  = new Object();
        Thread a = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (lock) {
                    counter.increase(1);
                }
            }
        });
        Thread b = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (lock) {
                    counter.increase(1);
                }
            }
        });
        Thread c = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (lock) {
                    counter.reduce(1);
                }
            }
        });
        a.start(); b.start(); c.start();
        a.join(); b.join(); c.join();
        Assertions.assertEquals(10000, counter.getCount());
    }

}
