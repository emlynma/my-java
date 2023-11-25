package com.emlyn.ma.base.concurrent;

import com.emlyn.ma.base.util.ConcurrentUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OSConcurrentTests {

    // 1. 竞争条件
    // 一切的一切，从竞争条件开始，多个线程读写共享内存，最后的结果取决于进程运行的精确时序，时序不同，结果可能不同，这种情况被称为竞争条件

    // 2. 临界区
    // 对共享内存进行访问的程序片段被称为临界区域。临界区的代码，是线程不安全的，如果能通过某些手段，使得多个进程不可能同时处于临界区域，就能避免竞争条件

    // 3. 互斥
    // 互斥是指，同一时刻只能有一个线程访问临界区域，其他线程必须等待，直到当前线程退出临界区域，才能进入临界区域，这样就避免了竞争条件

    // 4. 互斥的实现
    // 4.1. 原子操作
    // 4.2. 自旋等待
    // 4.3. 锁（互斥锁）
    // 4.4. 信号量（互斥量）

    // 4.1 原子操作，本身就是互斥的
    @Test
    void testCAS() {
        var i = new AtomicInteger(0);
        assertTrue(i.compareAndSet(0, 1)); // true
        assertFalse(i.compareAndSet(0, 1)); // false
    }

    // 4.2 自旋等待，CAS自旋，可以用来实现互斥，方法是在while循环中不断尝试CAS，直到成功为止
    @Test
    void testCASSpin() {
        TicketSystem ticketSystem = new TicketSystem(100);
        AtomicInteger lock = new AtomicInteger(0);

        Runnable task = () -> {
            int number;
            do { // 不停的抢票，直到票卖完

                while (!lock.compareAndSet(0, 1)) {}
                { // 临界区
                    number = ticketSystem.cell(Thread.currentThread().getName(), 1);
                }
                lock.set(0);

                ConcurrentUtils.yield();
            } while (number != 0);
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(task, "consumer_" + i).start());
        ConcurrentUtils.sleep(1000);
    }

    // 4.3 锁（互斥锁），synchronized，ReentrantLock
    @Test
    void testSynchronized() {
        TicketSystem ticketSystem = new TicketSystem(100);

        Runnable task = () -> {
            int number;
            do { // 不停的抢票，直到票卖完

                synchronized (ticketSystem)
                { // 临界区
                    number = ticketSystem.cell(Thread.currentThread().getName(), 1);
                }

                ConcurrentUtils.yield();
            } while (number != 0);
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(task, "consumer_" + i).start());
        ConcurrentUtils.sleep(1000);
    }
    @Test
    void testLock() {
        TicketSystem ticketSystem = new TicketSystem(100);
        Lock lock = new ReentrantLock();

        Runnable task = () -> {
            int number;
            do { // 不停的抢票，直到票卖完

                lock.lock();
                { // 临界区
                    number = ticketSystem.cell(Thread.currentThread().getName(), 1);
                }
                lock.unlock();

                ConcurrentUtils.yield();
            } while (number != 0);
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(task, "consumer_" + i).start());
        ConcurrentUtils.sleep(1000);
    }
    @Test
    void testConcurrentTicketSystem() {
        ConcurrentTicketSystem ticketSystem = new ConcurrentTicketSystem(100);

        Runnable task = () -> {
            int number;
            do { // 不停的抢票，直到票卖完

                number = ticketSystem.cell(Thread.currentThread().getName(), 1);

                ConcurrentUtils.yield();
            } while (number != 0);
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(task, "consumer_" + i).start());
        ConcurrentUtils.sleep(1000);
    }

    // 4.4 信号量（互斥量），Semaphore
    @Test
    void testSemaphore() {
        TicketSystem ticketSystem = new TicketSystem(100);
        var semaphore = new Semaphore(1);

        Runnable task = () -> {
            int number;
            do { // 不停的抢票，直到票卖完

                try { semaphore.acquire(); } catch (InterruptedException e) { throw new RuntimeException(e); }
                { // 临界区
                    number = ticketSystem.cell(Thread.currentThread().getName(), 1);
                }
                semaphore.release();

                ConcurrentUtils.yield();
            } while (number != 0);
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(task, "consumer_" + i).start());
        ConcurrentUtils.sleep(1000);
    }

    // 5. 同步
    // 同步是指，多个线程之间，按照一定的顺序执行，而不是同时执行，这样就避免了竞争条件
    // 同步的实现往往依赖于互斥，比如生产者消费者问题，生产者和消费者之间，必须互斥，才能保证生产者生产的资源，被消费者消费，而不是被其他生产者消费

    // 6. 同步的实现
    // 6.1. 条件变量
    // 6.2. 信号量（同步量）

    // 6.1 条件变量，wait notify，Condition
    @Test
    void testWaitNotify() {
        // 要解决生产者消费者问题
        ProductSystem productSystem = new ProductSystem();
        Object lock = new Object();

        Runnable produceTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环
                synchronized (lock) {
                    while (productSystem.getProducts() == 10) { // 这里要用while而不是if，如果用if，第一个醒来的生产者生产后，如果又唤醒了一个生产者，那么这个生产者就会直接生产，而不会再判断一次
                        try { lock.wait(); } catch (InterruptedException e) { throw new RuntimeException(e); }
                    }
                    productSystem.produce(Thread.currentThread().getName(), 1);
                    lock.notifyAll();
                }
                ConcurrentUtils.yield();
            }
        };

        Runnable consumeTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环
                synchronized (lock) {
                    while (productSystem.getProducts() == 0) {
                        try { lock.wait(); } catch (InterruptedException e) { throw new RuntimeException(e); }
                    }
                    productSystem.consume(Thread.currentThread().getName(), 1);
                    lock.notifyAll();
                }
                ConcurrentUtils.yield();
            }
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(produceTask, "producer_" + i).start());
        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(consumeTask, "consumer_" + i).start());
        ConcurrentUtils.sleep(1500);
        System.out.println("total: " + productSystem.getTotal());
    }
    @Test
    void testCondition() {
        // 要解决生产者消费者问题
        ProductSystem productSystem = new ProductSystem();
        Lock lock = new ReentrantLock();
        var productCondition = lock.newCondition();
        var consumeCondition = lock.newCondition();

        Runnable produceTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环
                lock.lock();
                {
                    while (productSystem.getProducts() == 10) { // 这里要用while而不是if，如果用if，第一个醒来的生产者生产后，如果又唤醒了一个生产者，那么这个生产者就会直接生产，而不会再判断一次
                        try { productCondition.await(); } catch (InterruptedException e) { throw new RuntimeException(e); }
                    }
                    productSystem.produce(Thread.currentThread().getName(), 1);
                    consumeCondition.signalAll();
                }
                lock.unlock();
                ConcurrentUtils.yield();
            }
        };

        Runnable consumeTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环
                lock.lock();
                {
                    while (productSystem.getProducts() == 0) {
                        try { consumeCondition.await(); } catch (InterruptedException e) { throw new RuntimeException(e); }
                    }
                    productSystem.consume(Thread.currentThread().getName(), 1);
                    productCondition.signalAll();
                }
                lock.unlock();
                ConcurrentUtils.yield();
            }
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(produceTask, "producer_" + i).start());
        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(consumeTask, "consumer_" + i).start());
        ConcurrentUtils.sleep(1500);
        System.out.println("total: " + productSystem.getTotal());
    }
    @Test
    void testConcurrentProductSystem() {
        // 要解决生产者消费者问题
        ConcurrentProductSystem productSystem = new ConcurrentProductSystem();

        Runnable produceTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环
                productSystem.produce(Thread.currentThread().getName(), 1);
                ConcurrentUtils.yield();
            }
        };

        Runnable consumeTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环
                productSystem.consume(Thread.currentThread().getName(), 1);
                ConcurrentUtils.yield();
            }
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(produceTask, "producer_" + i).start());
        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(consumeTask, "consumer_" + i).start());
        ConcurrentUtils.sleep(1500);
        System.out.println("total: " + productSystem.getTotal());
    }

    // 6.2 信号量（同步量），Semaphore
    @Test
    void testSemaphoreSync() {
        // 要解决生产者消费者问题，需要一把锁，用于所有生产者和消费者之间的互斥，还需要两个信号量，用于生产者和消费者之间的同步
        ProductSystem productSystem = new ProductSystem();
        var lock = new ReentrantLock();
        var empty = new Semaphore(10); // 10个空位，只能生产10个产品，如果超过10个产品，生产者必须等待
        var fully = new Semaphore(0);  // 10个满位，只能消费10个产品，如果没有产品，消费者必须等待

        Runnable produceTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环

                try {
                    empty.acquire(); // 有空位才能生产，消耗一个空位，没有空位时阻塞自己，用于同步和消费者但顺序
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                lock.lock();
                { // 临界区
                    productSystem.produce(Thread.currentThread().getName(), 1); // 消费一个产品
                }
                lock.unlock();

                fully.release(); // 释放一个满位，供消费者使用，或唤醒消费者使用

                ConcurrentUtils.yield();
            }
        };

        Runnable consumeTask = () -> {
            LocalDateTime endTime = LocalDateTime.now().plusSeconds(1);
            while (LocalDateTime.now().isBefore(endTime)) { // 1秒后结束循环

                try {
                    fully.acquire(); // 有满位才能消费，消耗一个满位，没有满位时阻塞自己
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                lock.lock();
                { // 临界区
                    productSystem.consume(Thread.currentThread().getName(), 1); // 生产一个产品
                }
                lock.unlock();

                empty.release(); // 释放一个空位，供生产者使用，或唤醒消费者使用

                ConcurrentUtils.yield();
            }
        };

        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(produceTask, "producer_" + i).start());
        IntStream.rangeClosed(1, 3).forEach(i -> new Thread(consumeTask, "consumer_" + i).start());
        ConcurrentUtils.sleep(2000);
        System.out.println("total: " + productSystem.getTotal());
    }






    @AllArgsConstructor
    private static class TicketSystem {

        private int tickets;

        public int cell(String customer, int number) {
            if (tickets < 0) {
                throw new RuntimeException("tickets can't < 0");
            }
            if (number <= 0) {
                System.out.println("invalid number: " + number);
                return 0;
            }
            if (number > tickets) {
                System.out.println("sorry, ticket not enough, remain " + tickets + " ticket");
                return 0;
            }
            tickets -= number;
            System.out.println("sale to " + customer + " " + number + " ticket, remain " + tickets);
            return number;
        }

    }


    private static class ConcurrentTicketSystem extends TicketSystem {

        public ConcurrentTicketSystem(int tickets) {
            super(tickets);
        }

        public synchronized int cell(String customer, int number) {
            return super.cell(customer, number);
        }

    }

    @Getter
    private static class ProductSystem {

        protected static final int MAX_PRODUCTS = 10;

        private int products;

        private int total = 0;

        public void produce(String producer, int number) {
            if (products < 0 || products > MAX_PRODUCTS) {
                throw new RuntimeException("products can't < 0 or > " + MAX_PRODUCTS);
            }
            if (number <= 0) {
                System.out.println("invalid number: " + number);
                return;
            }
            products += number;
            total += number;
            System.out.println(producer + " produce " + number+ " product, now remain " + products);
        }

        public void consume(String consumer, int number) {
            if (products < 0 || products > MAX_PRODUCTS) {
                throw new RuntimeException("products can't < 0 or > " + MAX_PRODUCTS);
            }
            if (number <= 0) {
                System.out.println("invalid number: " + number);
                return;
            }
            if (number > products) {
                System.out.println("sorry, product not enough, now remain " + products);
                return;
            }
            products -= number;
            System.out.println(consumer + " consume " + number + " product, now remain " + products);
        }

    }

    private static class ConcurrentProductSystem extends ProductSystem {

        public synchronized void produce(String producer, int number) {
            while (getProducts() == MAX_PRODUCTS) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            super.produce(producer, number);
            this.notifyAll();
        }

        public synchronized void consume(String consumer, int number) {
            while (getProducts() == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            super.consume(consumer, number);
            this.notifyAll();
        }

    }

}
