package com.emlynma.java.algo.search;

public class BinarySearch {

    // 迭代实现
    private int search_iteration(int[] array, int target) {
        // 准入校验
        if (array == null || array.length == 0) {
            return -1;
        }
        int i = 0; int j = array.length - 1;
        while (i <= j) {
            int m = (i + j) / 2;
            if (array[m] == target) {
                return m;
            } else if (array[m] < target) {
                i = m + 1;
            } else {
                j = m - 1;
            }
        }
        return -1;
    }

    // 递归实现
    private int search_recursion(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }
        return search_recursion_core(array, 0, array.length - 1, target);
    }
    // 递归核心：入参：数组，索引范围，目标值，返回值：索引
    private int search_recursion_core(int[] array, int i, int j, int target) {
        // 递归出口
        if (i > j) {
            return -1;
        }
        int m = (i + j) / 2;
        if (array[m] == target) {
            return m;
        }
        // 更新参数，调用自身
        if (array[m] < target) {
            return search_recursion_core(array, m + 1, j, target);
        } else {
            return search_recursion_core(array, i, m - 1, target);
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        BinarySearch binarySearch = new BinarySearch();
        System.out.println(binarySearch.search_iteration(array, 5));
        System.out.println(binarySearch.search_recursion(array, 5));
    }

}
