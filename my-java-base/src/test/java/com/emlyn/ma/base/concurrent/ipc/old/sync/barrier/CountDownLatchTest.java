package com.emlyn.ma.base.concurrent.ipc.old.sync.barrier;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * CountDownLatch 允许一个或多个线程等待其他线程完成操作
 */
public class CountDownLatchTest {

    @BeforeAll
    public static void init() throws IOException {
        LocalDateTime start = LocalDateTime.now();
        for (int i = 0; i < 3; i++) {
            File file = new File("./file_" + i);
            if (file.exists()) {
                if (!file.delete()) {
                    throw new RuntimeException("delete file fail");
                }
            }
            if (!file.createNewFile()) {
                throw new RuntimeException("create file fail");
            }
            try (FileWriter writer = new FileWriter(file)) {
                for (int j = 0; j < 1000000; j++) {
                    writer.append(String.valueOf(Double.valueOf(Math.random() * Long.MAX_VALUE).longValue())).append("\n");
                }
            }
        }
        LocalDateTime end = LocalDateTime.now();
        System.out.println("create file used " + Duration.between(start, end).getNano() + " ns");
    }

    @AfterAll
    public static void destroy() {
        for (int i = 0; i < 3; i++) {
             File file = new File("./file_" + i);
             if (file.exists()) {
                 if (!file.delete()) {
                     throw new RuntimeException("delete file fail");
                 }
             }
        }
    }

    @Test
    public void testCountDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        List<Future<List<String>>> futureList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            FutureTask<List<String>> futureTask = new FutureTask<>(() -> {
                Scanner scanner = new Scanner(new FileReader("./file_" + finalI));
                List<String> list = new LinkedList<>();
                while (scanner.hasNextLine()) {
                    list.add(scanner.nextLine());
                }
                countDownLatch.countDown();
                return list;
            });
            futureList.add(futureTask);
            new Thread(futureTask).start();
        }
        try {
            // 等待所有线程执行完毕，github copilot 太强了
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> sumList = new LinkedList<>();
        for (Future<List<String>> future : futureList) {
            try {
                sumList.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Assertions.assertEquals(3000000, sumList.size());
    }

}
