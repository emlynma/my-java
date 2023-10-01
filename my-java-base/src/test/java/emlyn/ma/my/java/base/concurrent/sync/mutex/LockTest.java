package emlyn.ma.my.java.base.concurrent.sync.mutex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 互斥同步（阻塞同步）之Lock接口，对应synchronized
 */
public class LockTest {

    static class Counter {
        private int count = 0;
        public int getCount() {
            return this.count;
        }
        public void increase(int n) {
            this.count += n;
        }
        public void reduce(int n) {
            this.count -= n;
        }
    }

    @Test
    public void testLock() throws InterruptedException {
        Counter counter = new Counter();
        Lock lock = new ReentrantLock();
        Thread a = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                counter.increase(1);
                lock.unlock();
            }
        });
        Thread b = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                counter.increase(1);
                lock.unlock();
            }
        });
        Thread c = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                counter.reduce(1);
                lock.unlock();
            }
        });
        a.start(); b.start(); c.start();
        a.join(); b.join(); c.join();
        Assertions.assertEquals(10000, counter.getCount());
    }

}
