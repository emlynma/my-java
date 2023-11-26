package com.emlyn.ma.base.concurrent;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class JVMConcurrentTests {

    @Test
    void testBaseMethod() throws Exception {
        Object object = new Object();
        Thread thread = Thread.currentThread();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        // 最底层的本地方法
        synchronized (object) {}
        Thread.sleep(10);
        Thread.yield();
        object.wait();
        object.wait(1000);
        object.notify();
        object.notifyAll();
        LockSupport.park();
        LockSupport.parkNanos(1000);
        LockSupport.unpark(Thread.currentThread());
        // 基于上述方法的封装
        thread.join(); // 基于 wait() 实现
        thread.join(1000);
        lock.lock(); // 基于 LockSupport.park() 实现
        condition.await(); // 基于 LockSupport.park() 实现

        // 状态机
        // RUNNABLE -> BLOCKED
        synchronized (object) {}

        // RUNNABLE -> WAITING
        object.wait();
        thread.join();
        LockSupport.park();

        // RUNNABLE -> TIMED_WAITING
        Thread.sleep(1000);
        object.wait(1000);
        thread.join(1000);
        LockSupport.parkNanos(1000);
    }

    @Test
    void testLock() throws InterruptedException {
        // TODO
    }

    @Test
    void testCondition() throws InterruptedException {
        // TODO
    }

    @Test
    void testCountDownLatch() throws InterruptedException {
        // CountDownLatch 是 Java 并发编程中的一个同步工具类，它允许一个或多个线程等待，直到其他线程完成一组操作。
        // CountDownLatch 初始化时需要指定一个计数值，await() 方法会阻塞当前线程，直到计数值减到零，而 countDown() 方法会将计数值减一。
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Runnable task = () -> {
            int ms = (int) (Math.random() * 1000);
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " sleep " + ms + "ms");
            countDownLatch.countDown();
        };
        IntStream.rangeClosed(1,3).forEach(i -> new Thread(task).start());
        countDownLatch.await();
    }

    @Test
    void testCyclicBarrier() throws InterruptedException {
        // 用于一组线程相互等待，直到所有线程都到达一个共同的屏障点。它允许一组线程相互等待，然后在达到屏障点后继续执行。
        // CyclicBarrier的计数器可以重置，可以被多次使用。
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        Runnable task = () -> {
            int ms = (int) (Math.random() * 1000);
            try {
                Thread.sleep(ms);
                System.out.println(Thread.currentThread().getName() + " sleep " + ms + "ms. now is " + LocalTime.now());
                cyclicBarrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " continue. now is " + LocalTime.now());
        };
        IntStream.rangeClosed(1,3).forEach(i -> new Thread(task).start());
        Thread.sleep(2000);
    }

    @Test
    void testSemaphore() throws InterruptedException {
        // 用于实现信号量机制。信号量是一种用于控制并发访问资源的机制，它可以限制同时访问某个资源的线程数量。
        // Semaphore 可以用来构建一些对象池，资源池之类的，比如数据库连接池。
        Semaphore semaphore = new Semaphore(3);
        Runnable task = () -> {
            try {
                semaphore.acquire();
                int ms = (int) (Math.random() * 1000);
                Thread.sleep(ms);
                System.out.println(Thread.currentThread().getName() + " sleep " + ms + "ms. now is " + LocalTime.now());
                semaphore.release();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        IntStream.rangeClosed(1,5).forEach(i -> new Thread(task).start());
        Thread.sleep(2000);
    }

    @Test
    void testExchanger() throws InterruptedException {
        // 用于在两个线程之间进行对象交换。它提供了一种同步点，当两个线程都到达该点时，它们可以交换彼此持有的对象。
        // Exchanger 可以用于遗传算法、校对工作和数据同步等场景。
        Exchanger<String> exchanger = new Exchanger<>();
        Runnable task = () -> {
            try {
                String name = Thread.currentThread().getName();
                String result = exchanger.exchange(name);
                System.out.println("Hey, I'm " + name + ", you are " + result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        IntStream.rangeClosed(1,2).forEach(i -> new Thread(task).start());
        Thread.sleep(2000);
    }

    @Test
    void testPhaser() throws InterruptedException {
        // 它提供了一种灵活的线程同步机制。与 CountDownLatch 和 CyclicBarrier 类似，Phaser 可以用于协调多个线程的执行。
        // 与 CountDownLatch 不同的是，Phaser 的注册数量可以动态变化，而不是在创建实例时固定。
        // 与 CyclicBarrier 不同的是，Phaser 可以支持更复杂的线程协作关系，比如注册数量的动态变化，分阶段执行任务等。
        // Phaser 可以用于代替 CountDownLatch 和 CyclicBarrier。
        Phaser phaser = new Phaser(1);
        Runnable task = () -> {
            phaser.register();
            try {
                int ms = (int) (Math.random() * 1000);
                Thread.sleep(ms);
                System.out.println(Thread.currentThread().getName() + " sleep " + ms + "ms. now is " + LocalTime.now());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + " continue. now is " + LocalTime.now());
            phaser.arriveAndDeregister();
        };
        IntStream.rangeClosed(1,3).forEach(i -> new Thread(task).start());
        phaser.arriveAndAwaitAdvance();
        System.out.println("main continue. now is " + LocalTime.now());
        phaser.arriveAndDeregister();
    }

    @Test
    void testLockSupport() throws InterruptedException {
        // TODO
    }

}
