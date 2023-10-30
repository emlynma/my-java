package emlyn.ma.my.java.base.concurrent.ipc.jvm;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class MutexSynchronizedTests {

    @Test
    void test() throws InterruptedException {
        TicketSystem ticketSystem = new TicketSystem(100);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            int n = i;
            Thread thread = new Thread(() -> {
                while (true) {
                    int number = ticketSystem.cell(Thread.currentThread().getName(), n);
                    if (number == 0) {
                        countDownLatch.countDown();
                        return;
                    }
                    randomSleep();
                }
            });
            thread.setName("consumer_" + n);
            thread.start();
        }
        countDownLatch.await();
    }

    @AllArgsConstructor
    private static class TicketSystem {

        private int tickets;

        public synchronized int cell(String customer, int number) {
            int n = Math.min(tickets, number);
            tickets -= n;
            if (n == 0) {
                System.out.println("sorry, ticket sold out");
            } else {
                System.out.println("sale to " + customer + " " + n + " ticket, remain " + tickets);
            }
            return n;
        }

    }

    private void randomSleep() {
        if (((int) (Math.random() * 100)) % 2 == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
