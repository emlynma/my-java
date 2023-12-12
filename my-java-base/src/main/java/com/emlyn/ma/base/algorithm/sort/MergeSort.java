package com.emlyn.ma.base.algorithm.sort;

public class MergeSort {

    public static int[] temp;

    public static void sort(int[] array) {
        temp = new int[array.length];
        sort(array, 0, array.length - 1);
    }

    // 核心递归方法
    private static void sort(int[] array, int l, int r) {
        // 递归出口
        if (l >= r) {
            return;
        }
        // 更新参数，调用自身
        int m = l + (r - l) / 2;
        sort(array, l, m);
        sort(array, m + 1, r);
        // 递归方法核心逻辑
        merge(array, l, m, r);
    }

    private static void merge(int[] array, int l, int m, int r) {
        System.arraycopy(array, l, temp, l, r - l + 1);
        int s1 = l, s2 = m + 1;
        for (int i = l; i <= r; i++) {
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

    public static void main(String[] args) {
        SortTest.test(MergeSort::sort);
    }

}
