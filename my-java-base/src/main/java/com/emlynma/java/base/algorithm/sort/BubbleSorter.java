package com.emlynma.java.base.algorithm.sort;

public class BubbleSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        for (var i = 0; i < array.length; i++) {
            for (var j = array.length - 1; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                }
            }
        }
    }
}
