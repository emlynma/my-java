package com.emlyn.ma.base.concurrent.ipc.old.sync.barrier;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 允许一组线程相互等待，直到到达某个公共屏障点
 */
public class CyclicBarrierTest {

    @Test
    public void test() throws InterruptedException, BrokenBarrierException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("all thread finished"));
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "I will be blocked");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "I will be blocked");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println(Thread.currentThread().getName() + "I will sleep 2s");
        Thread.sleep(2000);
        cyclicBarrier.await();
    }

}
