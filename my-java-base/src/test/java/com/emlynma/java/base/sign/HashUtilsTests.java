package com.emlynma.java.base.sign;

import org.junit.jupiter.api.Test;

public class HashUtilsTests {

    @Test
    void test() {
        System.out.println(HashUtils.encryptMD5("test"));
        System.out.println(HashUtils.encryptSHA("test"));
    }

}
