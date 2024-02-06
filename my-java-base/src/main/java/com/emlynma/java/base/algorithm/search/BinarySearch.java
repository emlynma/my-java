package com.emlynma.java.base.algorithm.search;

import com.emlynma.java.base.util.AlgorithmUtils;

public class BinarySearch {

    /**
     * 二分查找
     * @param key 查找值
     * @param array 被查数组，该数组必须是有序的，默认由小及大
     * @return 查找值在被查数组中的位置，查不到返回 -1
     */
    public static int rank(int key, int[] array) {
        int l = 0;
        int r = array.length - 1;
        while (l <= r) {           // 这里用 < 还是 <= 的
            int m = l + (r - l) / 2;
            if (array[m] > key) {
                r = m - 1;
            } else if (array[m] < key) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = AlgorithmUtils.generateSortedIntArray(5000000, true);
        int rank = BinarySearch.rank(20, array);
        if (rank == -1) {
            System.out.println("not found rank");
        } else {
            assert array[rank] == 20;
            System.out.println("executed successfully");
        }
    }

}
