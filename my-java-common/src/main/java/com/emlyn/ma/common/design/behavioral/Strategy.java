package com.emlyn.ma.common.design.behavioral;

public class Strategy {

    // 策略接口
    public interface SortingStrategy {
        void sort(int[] numbers);
    }

    // 具体策略类
    static class BubbleSort implements SortingStrategy {
        @Override
        public void sort(int[] numbers) {
            System.out.println("Sorting using bubble sort");
            // 实现冒泡排序算法
        }
    }

    static class QuickSort implements SortingStrategy {
        @Override
        public void sort(int[] numbers) {
            System.out.println("Sorting using quick sort");
            // 实现快速排序算法
        }
    }

    static class Sorter {
        private SortingStrategy sortingStrategy;

        public void setSortingStrategy(SortingStrategy sortingStrategy) {
            this.sortingStrategy = sortingStrategy;
        }

        public void performSort(int[] numbers) {
            sortingStrategy.sort(numbers);
        }
    }

    public static void main(String[] args) {
        int[] numbers = {5, 2, 8, 1, 9};

        Sorter sorter = new Sorter();

        // 使用冒泡排序策略
        sorter.setSortingStrategy(new BubbleSort());
        sorter.performSort(numbers);

        // 使用快速排序策略
        sorter.setSortingStrategy(new QuickSort());
        sorter.performSort(numbers);
    }

}
