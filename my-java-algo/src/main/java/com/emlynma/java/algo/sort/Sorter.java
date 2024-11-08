package com.emlynma.java.algo.sort;

public interface Sorter {

    void sort(int[] array);

    default void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    default boolean check(int[] array) {
        for (int i = 0; i + 1 < array.length; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

}
