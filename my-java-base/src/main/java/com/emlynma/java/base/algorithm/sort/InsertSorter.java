package com.emlynma.java.base.algorithm.sort;

public class InsertSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        for (var i = 1; i < array.length; i++) {
            for (var j = i; j - 1 >= 0; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }
}
