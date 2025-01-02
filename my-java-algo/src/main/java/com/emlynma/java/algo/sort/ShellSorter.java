package com.emlynma.java.algo.sort;

public class ShellSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        var h = 1;
        while (h < array.length / 3) {
            h = h * 3 + 1;
        }
        for (; h >= 1; h /= 3) {
            for (var i = h; i < array.length; i++) {
                for (var j = i; j - h >= 0; j -= h) {
                    if (array[j] < array[j - h]) {
                        swap(array, j, j - h);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
