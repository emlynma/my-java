package com.emlynma.java.base.algorithm.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class STTests {

    @Test
    void testSequentialSearchST() {
        testST(new SequentialSearchST<>());
    }

    void testST(ST<Integer, String> st) {
        if (Objects.isNull(st)) {
            return;
        }
        st.put(1, "11");

        st.put(1, "1");
        st.put(3, "3");
        st.put(5, "5");
        st.put(7, "7");
        st.put(8, "8");
        st.put(10, "10");
        st.put(12, "12");

        Assertions.assertEquals("1", st.get(1));
        Assertions.assertEquals(7, st.size());
        Assertions.assertEquals(5, st.select(2));
        Assertions.assertEquals(4, st.rank(8));
        Assertions.assertEquals(5, st.rank(9));
        Assertions.assertEquals(4, st.size(7, 12));
        Assertions.assertEquals(2, st.size(9, 12));
        Assertions.assertEquals(1, st.min());
        Assertions.assertEquals(12, st.max());

        st.deleteMin();
        st.deleteMax();

        Assertions.assertEquals(5, st.size());
        Assertions.assertEquals(3, st.min());
    }

}
