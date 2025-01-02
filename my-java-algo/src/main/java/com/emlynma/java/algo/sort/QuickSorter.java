package com.emlynma.java.algo.sort;

public class QuickSorter implements Sorter {
    @Override
    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    // 递归方法
    private void sort(int[] array, int l, int r) {
        // 递归出口
        if (l >= r) {
            return;
        }
        // 递归方法核心逻辑
        int m = partition(array, l, r);
        // 更新参数，调用自身
        sort(array, l, m - 1);
        sort(array, m + 1, r);
    }

    private int partition(int[] array, int l, int r) {
        swap(array, l, l + (r - l) / 2);
        var pivot = array[l];
        var s1 = l;
        var s2 = r + 1;
        while (true) {
            while (array[++s1] <= pivot && s1 < r) {
                continue;
            }
            while (array[--s2] > pivot && s2 > l) {
                continue;
            }
            if (s1 >= s2) { // 这里用=号是为了避免一边倒时的边界情况
                break;
            }
            swap(array, s1, s2);
        }
        swap(array, l, s2);
        return s2;
    }
}
