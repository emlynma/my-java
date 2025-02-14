package com.emlynma.java.base.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

public class HttpUtilsTests {

    @Test
    void testGet() {
        String url = "https://www.baidu.com";
        String result = HttpUtils.get(url);
        System.out.println(result);

        System.out.println("---");

        result = HttpUtils.get(url, Map.of("wd", "java"));
        System.out.println(result);
    }

    @Test
    void testPost() {
        String url = "https://www.baidu.com";
        String result = HttpUtils.post(url);
        System.out.println(result);

        System.out.println("---");

        result = HttpUtils.post(url, Map.of("wd", "java"));
        System.out.println(result);
    }

}
