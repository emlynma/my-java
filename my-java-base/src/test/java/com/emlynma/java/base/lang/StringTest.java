package com.emlynma.java.base.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Test
    void testString() {
        String str = "Hello World!";
        Assertions.assertEquals("Hello World!", str);
        Assertions.assertTrue("Hello World!" == str.toString());
        Assertions.assertFalse("Hello World!" == new String(str));
    }
}
