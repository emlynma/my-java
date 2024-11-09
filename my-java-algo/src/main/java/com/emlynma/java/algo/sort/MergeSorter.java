package com.emlynma.java.algo.sort;

public class MergeSorter implements Sorter {

    private final ThreadLocal<int[]> tempTL = new ThreadLocal<>();

    @Override
    public void sort(int[] array) {
        tempTL.set(new int[array.length]);
        sort(array, 0, array.length - 1);
    }

    // 递归方法
    private void sort(int[] array, int l, int r) {
        // 递归出口
        if (l >= r) {
            return;
        }
        // 更新参数，调用自身
        var m = (l + r) / 2;
        sort(array, l, m);
        sort(array, m + 1, r);
        // 核心逻辑
        merge(array, l, m, r);
    }

    private void merge(int[] array, int l, int m, int r) {
        var temp = tempTL.get();
        System.arraycopy(array, l, tempTL.get(), l, r - l + 1);
        var s1 = l;
        var s2 = m + 1;
        for (var i = l; i <= r; i++) {
            if (s1 > m) {
                array[i] = temp[s2++];
            } else if (s2 > r) {
                array[i] = temp[s1++];
            } else if (temp[s1] < temp[s2]) {
                array[i] = temp[s1++];
            } else {
                array[i] = temp[s2++];
            }
        }
    }
}
