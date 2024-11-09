package com.emlynma.java.algo.sort;

public class CountSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        var min = array[0];
        var max = array[0];
        for (var v : array) {
            min = Math.min(min, v);
            max = Math.max(max, v);
        }
        if (max == min) return;
        var count = new int[max - min + 1];
        for (var v : array) {
            count[v - min]++;
        }
        var index = 0;
        for (var c = 0; c < count.length; c++) {
            if (count[c] > 0) {
                for (var i = 0; i < count[c]; i++) {
                    array[index++] = c + min;
                }
            }
        }
    }
}
