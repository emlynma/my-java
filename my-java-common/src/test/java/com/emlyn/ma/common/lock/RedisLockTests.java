package com.emlyn.ma.common.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class RedisLockTests {

    @Test
    void testTryLock() throws InterruptedException {
        String key = "test_key";
        Lock lock = new RedisLock(key);

        Runnable task = () -> {
            if (lock.tryLock()) {
                System.out.println("I get lock");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.unlock();
            } else {
                System.out.println("no lock");
            }
        };

        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();

        Thread.sleep(1000);
    }

    @Test
    void testTryLockWithTimeout() throws InterruptedException {
        String key = "test_key";
        Lock lock = new RedisLock(key);

        Runnable task = () -> {
            try {
                if (lock.tryLock(400, TimeUnit.MILLISECONDS)) {
                    System.out.println("I get lock");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    lock.unlock();
                } else {
                    System.out.println("no lock");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();

        Thread.sleep(1000);
    }

    @Test
    void testLock() throws InterruptedException {
        String key = "test_key";
        Lock lock = new RedisLock(key);

        Runnable task = () -> {
            lock.lock();
            System.out.println("I get lock");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.unlock();
        };

        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();

        Thread.sleep(1000);
    }

}
