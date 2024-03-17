package com.emlynma.java.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RetryUtilsTests {

    @Test
    void testExecute() {
        // 一般场景
        String result = RetryUtils.execute(() -> {
            System.out.println("get string");
            return "string";
        });
        Assertions.assertEquals("string", result);
        // 捕获异常
        result = RetryUtils.execute(() -> {
            System.out.println("throw exception");
            throw new RuntimeException();
        }, (e) -> {
            System.out.println("handle exception");
        });
        Assertions.assertNull(result);
        // 结果判断
        result = RetryUtils.execute(() -> {
            System.out.println("get string");
            return "string";
        }, null, "str"::equals);
        Assertions.assertEquals("string", result);
        // 重试次数
        result = RetryUtils.execute(() -> {
            System.out.println("get string");
            return "string";
        }, null, "str"::equals, 3, 0);
        Assertions.assertEquals("string", result);
        // 重试间隔
        result = RetryUtils.execute(() -> {
            System.out.println("get string");
            return "string";
        }, null, "str"::equals, 3, 500);
        Assertions.assertEquals("string", result);
    }

}
