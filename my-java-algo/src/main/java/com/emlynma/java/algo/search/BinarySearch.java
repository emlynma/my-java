package com.emlynma.java.algo.search;

public class BinarySearch {
    public static void main(String[] args) {
        int[] array = new int[] {1,2,3,5,6,8,9};
//        int index = binarySearch(array, 7);
//        System.out.println(index == -1);
        int index = binarySearch2(array, 3);
        System.out.println(index == 2);
    }

    // iteration
    private static int binarySearch(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int i = 0, j = array.length - 1;
        while (i <= j) {
            int m = i + (j - i) / 2;
            if (array[m] == target) {
                return m;
            } else if (array[m] > target) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return -1;
    }

    // recursion
    private static int binarySearch2(int[] array, int target) {
        return binary(array, 0, array.length - 1, target);
    }

    private static int binary(int[] array, int i, int j, int target) {
        // 递归出口
        if (i > j) {
            return -1;
        }
        int m = i + (j - i) / 2;
        if (array[m] == target) {
            return m;
        }
        // 更新参数
        if (array[m] > target) {
            j = m -1;
        } else {
            i = m + 1;
        }
        // 调用自身
        return binary(array, i, j, target);
    }
}
