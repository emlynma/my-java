package emlyn.ma.my.java.base.concurrent;

import org.junit.jupiter.api.Test;

/**
 * volatile关键字测试
 * 保证可见性
 * 不保证原子性
 */
public class VolatileTest {

    private int count = 0;
    private volatile int volatileCount = 0;
    private volatile int volatileCount2 = 0;

    /**
     * 可见性测试：普通变量不保证可见性，volatile变量保证可见性
     */
    @Test
    public void testVisibility() throws InterruptedException {
        // t1一直打印count的值
        new Thread(() -> {
           while (count == 0) {}
            System.out.println("看到了count的值变为了" + count);
        }).start();
        // t2一直打印volatileCount的值
        new Thread(() -> {
            while (volatileCount == 0) {}
            System.out.println("看到了volatileCount的值变为了" + volatileCount);
        }).start();

        // 1s后修改count、volatileCount的值，看t1、t2能不能立即看到
        Thread.sleep(1000);
        new Thread(() -> {
            count = 1;
            volatileCount = 1;
        }).start();

        // 2s后结束t1
        Thread.sleep(2000);
    }

    // 原子性测试：volatile变量不保证原子性
    @Test
    public void testAtomicity() {
        // 两个线程同时对count进行10000次自增
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
                volatileCount++;
                volatileCount2 = volatileCount2 + 1;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
                volatileCount++;
                volatileCount2 = volatileCount2 + 1;
            }
        });
        t1.start();
        t2.start();
        // 等待两个线程结束
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 期望count的值为20000，但是实际上不是
        System.out.println("count的值为" + count);
        System.out.println("volatileCount的值为" + volatileCount);
        System.out.println("volatileCount2的值为" + volatileCount2);
    }
}
