package com.emlyn.ma.base.concurrent.ipc.old.os;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * 信号量，
 * 多人抢票问题
 * 生产者消费者问题
 */
public class MutexSemaphoreTests {

    private static int interrupt = 100;

    @Test
    void test() throws InterruptedException {
        ResourceSystem resourceSystem = new ResourceSystem(0);
        Semaphore mutex = new Semaphore(1);
        Semaphore empty = new Semaphore(10);
        Semaphore fully = new Semaphore(0);

        CountDownLatch countDownLatch = new CountDownLatch(4);

        for (int i = 1; i <= 2; i++) {
            String name = "producer_" + i;
            new Thread(() -> {
                while (true) {
                    try {
                        empty.acquire(); // 有空位才能生产，消耗一个空位，没有空位时阻塞自己，用于同步和消费者但顺序
                        mutex.acquire(); // 用于产生互斥临界区，防止多个生产者产生竞争，导致生产错误
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    resourceSystem.product(name); // 生产一个资源

                    mutex.release();
                    fully.release(); // 释放一个满位，供消费者使用或唤醒消费者使用

                    if (interrupt-- < 0) countDownLatch.countDown(); // 中断，避免程序一直运行下去
                }
            }, name).start();
        }

        for (int i = 1; i <= 2; i++) {
            String name = "consumer_" + i;
            new Thread(() -> {
                while (true) {
                    try {
                        fully.acquire(); // 有满位才能消费，消耗一个满位，没有满位时阻塞自己
                        mutex.acquire(); // 用于产生互斥临界区
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    resourceSystem.consume(name); // 消费一个资源

                    mutex.release();
                    empty.release(); // 释放一个空位，供生产者使用或唤醒消费者使用

                    if (interrupt-- < 0) countDownLatch.countDown(); // 中断，避免程序一直运行下去
                }
            }, name).start();
        }

        countDownLatch.await();
    }

    @AllArgsConstructor
    private static class ResourceSystem {

        private int resources;

        public int getResources() {
            return resources;
        }

        public void product(String producer) {
            resources += 1;
            System.out.println(producer + " product a resource, now remain " + resources);
        }

        public void consume(String consumer) {
            resources -= 1;
            if (resources < 0) {
                throw new RuntimeException("resource can't < 0");
            }
            System.out.println(consumer + " consume a resource, now remain " + resources);
        }

    }

}
