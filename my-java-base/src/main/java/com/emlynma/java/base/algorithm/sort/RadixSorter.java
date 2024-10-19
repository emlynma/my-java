package com.emlynma.java.base.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

public class RadixSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        var max = array[0];
        for (var v : array) {
            max = Math.max(max, v);
        }
        for (int digit = 1; max / digit > 0; digit *= 10) {
            sortByDigit(array, digit);
        }
    }

    private void sortByDigit(int[] array, int digit) {
        var buckets = new ArrayList<List<Integer>>();
        for (var i = 0; i < 10; i++) {
            buckets.add(new ArrayList<>());
        }
        for (var v : array) {
            var index = (v / digit) % 10;
            buckets.get(index).add(v);
        }
        var index = 0;
        for (var bucket : buckets) {
            for (var v : bucket) {
                array[index++] = v;
            }
        }
    }
}
