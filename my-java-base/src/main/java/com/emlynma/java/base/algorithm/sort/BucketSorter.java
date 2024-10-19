package com.emlynma.java.base.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        var min = array[0];
        var max = array[0];
        for (var v : array) {
            min = Math.min(min, v);
            max = Math.max(max, v);
        }
        if (max == min) return;
        var buckets = new ArrayList<List<Integer>>();
        for (var i = 0; i < 10; i++) {
            buckets.add(new ArrayList<>());
        }
        for (var v : array) {
            var index = ((v - min) / (max - min) * (10 - 1));
            buckets.get(index).add(v);
        }
        var index = 0;
        for (var bucket : buckets) {
            Collections.sort(bucket);
            for (var v : bucket) {
                array[index++] = v;
            }
        }
    }
}
