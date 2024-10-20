package com.emlynma.java.base.algorithm.search;

public class DictionaryTest {
    public static void main(String[] args) {
        Dictionary<Integer, String> dictionary = new LinkedDictionary<>();
        dictionary.put(0, "zero");
        dictionary.put(1, "one");
        dictionary.put(2, "two");
        dictionary.put(3, "three");
        dictionary.put(4, "four");
        dictionary.put(5, "five");
        dictionary.put(6, "six");
        dictionary.put(7, "seven");
        dictionary.put(8, "eight");
        dictionary.put(9, "nine");
        assert dictionary.size() == 10;
        for (Integer key : dictionary.keys()) {
            System.out.print(key);
        }
        System.out.println();
        for (Integer key : dictionary.keys(3, 7)) {
            System.out.print(key);
        }
        System.out.println();
        assert dictionary.get(3).equals("three");
        assert dictionary.min() == 0;
        assert dictionary.max() == 9;
        assert dictionary.floor(5) == 4;
        assert dictionary.ceiling(5) == 6;
        assert dictionary.rank(5) == 5;
        assert dictionary.select(5) == 5;
        dictionary.deleteMin();
        dictionary.deleteMax();
        assert dictionary.size() == 8;
        for (Integer key : dictionary.keys()) {
            System.out.print(key);
        }
    }
}
