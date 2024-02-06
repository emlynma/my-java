package com.emlynma.java.base.concurrent.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {

    @Test
    public void testRunnable() throws InterruptedException, ExecutionException {
        // 创建线程池
        ThreadPoolExecutor threadPool_3_5_10 = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));

        Runnable sleepTask = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": I will sleep 1s");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // 向线程池提交任务
        for (int i = 0 ; i < 7; i++) {
            threadPool_3_5_10.execute(sleepTask);
        }

        Thread.sleep(11000);

        // test with submit
        Future<?> future = threadPool_3_5_10.submit(sleepTask);
        Assertions.assertNull(future.get());
    }

    @Test
    public void testCallable() throws InterruptedException, ExecutionException {
        // 创建线程池
        ThreadPoolExecutor threadPool_3_5_10 = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));

        Callable<Integer> sleepTask = () -> {
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

        // 向线程池提交任务
        List<Future<Integer>> futureList = new ArrayList<>(10);
        for (int i = 0 ; i < 7; i++) {
            futureList.add(threadPool_3_5_10.submit(sleepTask));
        }

        for (Future<Integer> future : futureList) {
            System.out.println(future.get());
        }
    }

    @Test // 自带的线程池
    public void testExecutors() {
        // ThreadPoolExecutor -> ExecutorService -> Executor
        ExecutorService executorService;

        // FixedThreadPool 使用固定线程数 + 无界阻塞队列
        executorService = Executors.newFixedThreadPool(5);

        // SingleThreadExecutor 使用单一线程数 + 无界阻塞队列
        executorService = Executors.newSingleThreadExecutor();

        // CachedThreadPool 使用无数线程数 + 不存储元素的阻塞队列
        executorService = Executors.newCachedThreadPool();

        // ScheduledThreadPoolExecutor -> ScheduledExecutorService -> ExecutorService -> Executor
        ScheduledExecutorService scheduledExecutorService;

        // ScheduledThreadPool
        scheduledExecutorService = Executors.newScheduledThreadPool(5);

        // SingleThreadScheduledExecutor
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

}
