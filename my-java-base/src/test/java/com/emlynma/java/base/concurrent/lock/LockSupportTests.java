package com.emlynma.java.base.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTests {

    @Test
    void test() throws InterruptedException {
        Runnable task = () -> {
            System.out.println("task start");
            LockSupport.park();
            System.out.println("task end");
        };

        Thread thread = new Thread(task);
        thread.start();

        Thread.sleep(1000);
        thread.wait();
        LockSupport.unpark(thread);
    }

}
