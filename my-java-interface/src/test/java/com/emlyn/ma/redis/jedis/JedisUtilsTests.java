package com.emlyn.ma.redis.jedis;

import org.junit.jupiter.api.Test;

public class JedisUtilsTests {

    @Test
    void testGet() {
        JedisUtils.set("hello", "world");
        JedisUtils.set("hello", "world", 10);
        System.out.println(JedisUtils.get("hello"));
    }

    @Test
    void testSet() {
        for (int i = 0; i < 100; i++) {
            JedisUtils.set("hello" + i, "world" + i);
        }
    }

}
