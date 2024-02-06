package com.emlynma.java.base.algorithm.sort;

import com.emlynma.java.base.util.AlgorithmUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class SortTest {

    private static final int COUNT = 100000;

    public static void test(Consumer<int[]> sort) {
        test(AlgorithmUtils.generateRandomIntArray(COUNT, COUNT), "random:", sort);
        test(AlgorithmUtils.generateAscIntArray(COUNT), "asc: ", sort);
        test(AlgorithmUtils.generateDescIntArray(COUNT), "desc: ", sort);
    }

    private static void test(int[] array, String name, Consumer<int[]> sort) {
        LocalDateTime start = LocalDateTime.now();
        sort.accept(array);
        LocalDateTime end = LocalDateTime.now();
        check(array);
        Duration duration = Duration.between(start, end);
        System.out.println(name + "\t" + duration.toString());
    }

    public static void check(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                throw new RuntimeException("sorted array check failed");
            }
        }
    }

}
