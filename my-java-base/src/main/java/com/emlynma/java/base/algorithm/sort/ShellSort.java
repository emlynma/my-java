package com.emlynma.java.base.algorithm.sort;

public class ShellSort {

    public static void sort(int[] array) {
        int h = 1;
        while (array.length / 3 > h) {
            h = h * 3;
        }
        for (; h >= 1; h /= 3) {
            for (int i = h; i < array.length; i++) {
                for (int j = i; j >= h && array[j] < array[j - h]; j -= h) {
                    swap(array, j, j - h);
                }
            }
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        SortTest.test(ShellSort::sort);
    }

}
