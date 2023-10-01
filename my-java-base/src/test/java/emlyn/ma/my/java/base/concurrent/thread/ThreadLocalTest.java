package emlyn.ma.my.java.base.concurrent.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testThreadLocal() throws InterruptedException {

        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        new Thread(() -> {
            threadLocal.set("a");
            System.out.println(threadLocal.get());
            Assertions.assertEquals("a", threadLocal.get());
        }).start();

        new Thread(() -> {
            threadLocal.set("b");
            System.out.println(threadLocal.get());
            Assertions.assertEquals("b", threadLocal.get());
        }).start();

        Thread.sleep(100);
    }

}
