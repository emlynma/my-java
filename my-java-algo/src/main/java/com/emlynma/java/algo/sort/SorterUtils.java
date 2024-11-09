package com.emlynma.java.algo.sort;

import java.util.Arrays;

public abstract class SorterUtils {

    public static int[] createRandomIntArray(int count) {
        var array = new int[count];
        for (var i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * count);
        }
        return array;
    }

    public static int[] createSortedIntArray(int count) {
        var array = new int[count];
        for (var i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int[] createAesIntArray(int count) {
        var array = new int[count];
        for (var i = 0; i < array.length; i++) {
            array[i] = i;
            if ((i + 1) % 7 == 0) {
                array[i] = array[i - 2];
            }
        }
        return array;
    }

    public static int[] createDesIntArray(int count) {
        var array = new int[count];
        for (var i = 0; i < array.length; i++) {
            array[i] = array.length - i;
            if ((i + 1) % 7 == 0) {
                array[i] = array[i - 2];
            }
        }
        return array;
    }

    public static int[] createSameIntArray(int count) {
        var array = new int[count];
        Arrays.fill(array, 100);
        return array;
    }

}
