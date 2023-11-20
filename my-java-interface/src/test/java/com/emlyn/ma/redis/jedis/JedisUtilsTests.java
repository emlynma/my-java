package com.emlyn.ma.redis.jedis;

import org.junit.jupiter.api.Test;

public class JedisUtilsTests {

    @Test
    void testSet() {
        JedisUtils.set("hello", "world");
        JedisUtils.set("hello", "world", 10);
        System.out.println(JedisUtils.get("hello"));
    }

}
