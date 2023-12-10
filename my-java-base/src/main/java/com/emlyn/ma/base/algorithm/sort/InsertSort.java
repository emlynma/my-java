package com.emlyn.ma.base.algorithm.sort;

public class InsertSort {

    public static void sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j >= 1 && array[j] < array[j - 1]; j--) {
                swap(array, j, j - 1);
            }
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        SortTest.test(InsertSort::sort);
    }

}
