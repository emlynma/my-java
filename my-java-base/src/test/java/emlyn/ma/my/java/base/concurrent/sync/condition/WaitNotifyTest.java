package emlyn.ma.my.java.base.concurrent.sync.condition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 等待唤醒之wait() notify()
 */
public class WaitNotifyTest {

    static class TaskQueue {
        Queue<Object> taskQueue = new LinkedList<>();
        public synchronized void addTask(Object task) {
            taskQueue.add(task);
            this.notifyAll();
        }
        public synchronized Object getTask() throws InterruptedException {
            while (taskQueue.isEmpty()) {
                this.wait();
            }
            return taskQueue.remove();
        }
        public int getTaskCount() {
            return taskQueue.size();
        }
    }

    /**
     * 等待
     * 生产者-消费者
     * 1:1的情况下，getTask使用if足够
     * 1:n的情况下，需要使用while替代if
     */
    @Test
    public void testWaitNotify() throws InterruptedException {
        TaskQueue taskQueue = new TaskQueue();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                taskQueue.addTask("task-" + i);
                Thread.yield();
            }
        });
        Thread customer1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    taskQueue.getTask();
                    Thread.yield();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread customer2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    taskQueue.getTask();
                    Thread.yield();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start(); customer1.start(); customer2.start();
        producer.join(); customer1.join(); customer2.join();
        Assertions.assertEquals(0, taskQueue.getTaskCount());
    }

}
