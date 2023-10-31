package emlyn.ma.my.java.base.concurrent.ipc.os;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncConditionTests {

    private static int interrupt = 100;

    @Test
    void test() throws InterruptedException {
        ResourceSystem resourceSystem = new ResourceSystem(0);

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            while (interrupt-- > 0) {
                lock.lock();
                while (resourceSystem.getResources() > 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                resourceSystem.product("producer");
                condition.signalAll();
                lock.unlock();
            }
            countDownLatch.countDown();
        }).start();

        Thread b = new Thread(() -> {
            while (interrupt-- > 0) {
                lock.lock();
                while (resourceSystem.getResources() <= 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                resourceSystem.consume("consumer");
                condition.signalAll();
                lock.unlock();
            }
            countDownLatch.countDown();
        });

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
