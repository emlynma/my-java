package com.emlynma.java.base.concurrent.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadTest {

    /**
     * 线程的最基本操作：开一个线程执行一个任务
     */
    @Test
    public void testRunnable() throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": I will sleep 1s");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread newThread = new Thread(task);
        newThread.start();

        // 等待该线程执行完成，底层使用wait/notify机制实现
        newThread.join();
    }

    /**
     * 线程的最基本操作：开一个线程执行一个任务，并等待任务结束后拿到返回值
     */
    @Test
    public void testCallable() throws InterruptedException, ExecutionException {
        Callable<Integer> task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": I will sleep 1s");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return 0;
        };

        // Callable不能直接传给Thread，需要使用FutureTask包一层
        FutureTask<Integer> futureTask = new FutureTask<>(task);

        Thread newThread = new Thread(futureTask);
        newThread.start();

        // FutureTask 实现了 Future接口，支持阻塞式获取异步结果
        Integer result = futureTask.get();
        Assertions.assertEquals(0, result);
    }

    @Test
    public void testCallableCancel() throws Exception {
        Callable<Integer> task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": I will sleep 1s");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return 0;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(task);
        Thread newThread = new Thread(futureTask);
        newThread.start();

        // 先等它执行5秒后取消任务
        newThread.join(2000);
        futureTask.cancel(true);

        // 任务取消后，线程随之结束
        Assertions.assertTrue(futureTask.isCancelled());
        Assertions.assertFalse(newThread.isAlive());
    }

    @Test
    public void test() throws InterruptedException {
        Runnable task = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + ": I am running");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread newThread = new Thread(task);
        newThread.start();

        // 2秒后中断newThread
        Thread.sleep(2000);
        newThread.interrupt();
        Thread.sleep(2000);
        System.out.println("main thread exit");
    }

}
