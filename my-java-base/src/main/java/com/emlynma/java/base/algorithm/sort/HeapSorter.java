package com.emlynma.java.base.algorithm.sort;

public class HeapSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        for (var i = (array.length - 1) / 2; i >= 0; i--) {
            sink(array, i, array.length);
        }
        for (var i = array.length - 1; i > 0; i--) {
            swap(array, i, 0);
            sink(array, 0, i);
        }
    }

    // 上浮
    private void swim(int[] array, int i) {
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
    private void sink(int[] array, int i, int N) {
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
}
