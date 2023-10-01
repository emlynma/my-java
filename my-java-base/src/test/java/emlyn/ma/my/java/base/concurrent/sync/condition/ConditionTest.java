package emlyn.ma.my.java.base.concurrent.sync.condition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 等待唤醒之Condition接口，对应wait() notify()
 */
public class ConditionTest {

    static class TaskQueue {
        Queue<Object> taskQueue = new LinkedList<>();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        public void addTask(Object task) {
            lock.lock();
            taskQueue.add(task);
            condition.signal();
            lock.unlock();
        }
        public Object getTask() throws InterruptedException {
            lock.lock();
            while (taskQueue.isEmpty()) {
                condition.await();
            }
            Object task = taskQueue.remove();
            lock.unlock();
            return task;
        }
        public int getTaskCount() {
            return taskQueue.size();
        }
    }

    @Test
    public void testAwaitSignal() throws InterruptedException {
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
