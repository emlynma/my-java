package com.emlynma.java.algo.sort;

public class SelectSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        for (var i = 0; i < array.length; i++) {
            var m = i;
            for (var j = i + 1; j < array.length; j++) {
                if (array[j] < array[m]) {
                    m = j;
                }
            }
            swap(array, m, i);
        }
    }
}
