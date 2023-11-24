package com.emlyn.ma.base.concurrent.ipc.old.os;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS自旋，可以用来实现互斥，但不能实现同步
 * 多人抢票问题
 */
public class MutexCASSpinTests {

    @Test
    void test() throws InterruptedException {
        TicketSystem ticketSystem = new TicketSystem(100);
        AtomicInteger lock = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            int n = i;
            Thread thread = new Thread(() -> {
                while (true) {
                    while (!lock.compareAndSet(0, 1)) {}
                    int number = ticketSystem.cell(Thread.currentThread().getName(), n);
                    if (number == 0) {
                        lock.set(0);
                        countDownLatch.countDown();
                        return;
                    }
                    lock.set(0);
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

        public int cell(String customer, int number) {
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
