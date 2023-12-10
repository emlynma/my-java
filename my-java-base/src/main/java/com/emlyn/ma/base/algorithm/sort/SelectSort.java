package com.emlyn.ma.base.algorithm.sort;

public class SelectSort {

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int m = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[m]) {
                    m = j;
                }
            }
            swap(array, m, i);
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        SortTest.test(SelectSort::sort);
    }

}
