package com.emlynma.java.base.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTests {

    @Test
    void testLock() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println("locked");
        } finally {
            lock.unlock();
        }
    }

    @Test
    void testTryLock() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Runnable task = () -> {
            if (lock.tryLock()) {
                System.out.println("locked");
                lock.unlock();
            } else {
                System.out.println("no lock");
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }
        Thread.sleep(1000);
    }

    @Test
    void testTryLockTimeout() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Runnable task = () -> {
            try {
                if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                    System.out.println("locked");
                    lock.unlock();
                } else {
                    System.out.println("no lock");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }
        Thread.sleep(1000);
    }

    @Test
    void testLockInterruptibly() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Runnable task = () -> {
            try {
                lock.lockInterruptibly();
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println("sleep 1000");
                }
                lock.unlock();
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        };

        Thread thread1 = new Thread(task);
        thread1.start();
        String a = "";
        a.hashCode();

        Thread.sleep(3000);
        System.out.println("interrupt it");
        thread1.interrupt();
    }

}
