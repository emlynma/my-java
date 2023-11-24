package com.emlyn.ma.base.concurrent.ipc.old.sync.condition;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;

/**
 * 等待唤醒之LockSupport接口，对应wait() notify()
 */
public class LockSupportTest {

    @Test
    void test() throws InterruptedException {
        Queue<Object> taskQueue = new LinkedList<>();

        Thread customerThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (taskQueue.isEmpty()) {
                    LockSupport.park();
                }
                System.out.println(taskQueue.remove());
            }
        });

        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                taskQueue.add("task-" + i);
                LockSupport.unpark(customerThread);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producerThread.start();
        customerThread.start();

        producerThread.join();
        customerThread.join();
    }

}
