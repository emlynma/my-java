package com.emlynma.java.base.util;

public abstract class AlgorithmUtils {

    public static int[] generateSortedIntArray(int count, boolean asc) {
        int[] array = new int[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int[] generateRandomIntArray(int max, int count) {
        int[] array = new int[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * max);
        }
        return array;
    }

    public static int[] generateAscIntArray(int count) {
        int[] array = new int[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
            if ((i + 1) % 7 == 0) {
                array[i] = array[i - 2];
            }
        }
        return array;
    }

    public static int[] generateDescIntArray(int count) {
        int[] array = new int[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i;
            if ((i + 1) % 7 == 0) {
                array[i] = array[i - 2];
            }
        }
        return array;
    }

}
