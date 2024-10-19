package com.emlynma.java.base.algorithm.sort;

import com.emlynma.java.base.util.AlgorithmUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SorterTest {

    private static final int COUNT = 10000;

    private static final int[] RANDOM_ARRAY = AlgorithmUtils.generateRandomIntArray(COUNT, COUNT);
    private static final int[] ASC____ARRAY = AlgorithmUtils.generateAscIntArray(COUNT);
    private static final int[] DESC___ARRAY = AlgorithmUtils.generateDescIntArray(COUNT);
    private static final int[] SAME___ARRAY = AlgorithmUtils.generateDuplicateIntArray(COUNT);

    public static void main(String[] args) {
        var executor = Executors.newFixedThreadPool(40);
        var sorterList = List.of(
                new SelectSorter(),
                new BubbleSorter(),
                new InsertSorter(),
                new ShellSorter(),
                new CountSorter(),
                new BucketSorter(),
                new RadixSorter(),
                new MergeSorter(),
                new QuickSorter(),
                new HeapSorter()
        );
        var futureList = new ArrayList<Future<SortResult>>();
        for (var sorter : sorterList) {
            for (var type : SortType.values()) {
                futureList.add(executor.submit(new SortTask(sorter, type)));
            }
        }
        futureList.stream()
                .map(SorterTest::futureGet)
                .forEach(System.out::println);
        executor.shutdown();
    }

    public static void test(Sorter sorter) {
        var executor = Executors.newFixedThreadPool(4);
        Arrays.stream(SortType.values())
                .map(sortType -> new SortTask(sorter, sortType))
                .map(executor::submit)
                .map(SorterTest::futureGet)
                .forEach(System.out::println);
        executor.shutdown();
    }

    @SneakyThrows
    private static <T> T futureGet(Future<T> future) {
        return future.get(); // 这里会自动处理被检查异常
    }

    @AllArgsConstructor @Getter
    private enum SortType {
        RANDOM("random", RANDOM_ARRAY),
        ASC___("asc___", ASC____ARRAY),
        DESC__("desc__", DESC___ARRAY),
        SAME__("same__", SAME___ARRAY);
        private final String name;
        private final int[] array;
    }

    private record SortResult(Sorter sorter, SortType sortType, boolean sorted, Duration duration) {
        @Override
        public String toString() {
            return sorter.getClass().getSimpleName() + "::\t" + sortType.name + "\t" + sorted + "\t" + duration;
        }
    }

    private record SortTask(Sorter sorter, SortType sortType) implements Callable<SortResult> {
        @Override
        public SortResult call() {
            var array = Arrays.copyOf(sortType.getArray(), sortType.getArray().length);
            var str = LocalDateTime.now();
            sorter.sort(array);
            var end = LocalDateTime.now();
            var duration = Duration.between(str, end);
            var sorted = sorter.check(array);
            return new SortResult(sorter, sortType, sorted, duration);
        }
    }

}
