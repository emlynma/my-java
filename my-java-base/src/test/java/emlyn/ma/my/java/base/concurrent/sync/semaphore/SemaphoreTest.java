package emlyn.ma.my.java.base.concurrent.sync.semaphore;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 允许多个线程同时访问
 */
public class SemaphoreTest {

    @Test
    public void test() throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        // 10个线程同时执行，只有3个线程能够获取到信号量，其他线程会被阻塞
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "I will be blocked");
                    Thread.sleep(1000);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
        // 等待所有线程执行完毕
        countDownLatch.await();
    }

}
