package com.emlyn.ma.base.algorithm.sort;

public class QuickSort {

    public static void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    // 核心递归方法
    private static void sort(int[] array, int l, int r) {
        // 递归出口
        if (l >= r) {
            return;
        }
        // 递归方法核心逻辑
        int m = part(array, l, r);
        // 更新参数，调用自身
        sort(array, l, m - 1);
        sort(array, m + 1, r);
    }

    private static int part(int [] array, int l, int r) {
        swap(array, l, l + (r - l) / 2);
        int mid = array[l];
        int s1 = l, s2 = r + 1;
        while (true) {
            while (array[++s1] <= mid && s1 < r) {
                continue;
            }
            while (array[--s2] > mid && s2 > l) {
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

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        SortTest.test(QuickSort::sort);
    }

}
