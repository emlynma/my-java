package com.emlyn.ma.base.algorithm.sort;

public class HeapSort {

    private static void sort(int[] array) {
        for (int i = (array.length - 1) / 2; i >= 0; i--) {
            sink(array, i, array.length);
        }
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, i, 0);
            sink(array, 0, i);
        }
    }

    // 上浮
    private static void swim(int[] array, int i) {
        int j;
        while ((j = (i - 1) / 2) >= 0) {
            if (array[i] > array[j]) {
                swap(array, i, j);
                i = j;
            } else {
                break;
            }
        }
    }

    // 下沉
    private static void sink(int[] array, int i, int N) {
        int j;
        while ((j = i * 2 + 1) < N) {
            if ((j + 1 < N) && array[j + 1] > array[j]) {
                j = j + 1;
            }
            if (array[i] < array[j]) {
                swap(array, i, j);
                i = j;
            } else {
                break;
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        SortTest.test(HeapSort::sort);
    }

}
