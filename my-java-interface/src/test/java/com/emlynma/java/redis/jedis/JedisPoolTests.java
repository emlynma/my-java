package com.emlynma.java.redis.jedis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTests {

    private static JedisPool jedisPool;

    @BeforeAll
    static void beforeAll() {
        jedisPool = new JedisPool("localhost", 6379);
    }

    @AfterAll
    static void afterAll() {
        jedisPool.close();
    }

    @Test
    void testString() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("hello", "world");
            jedis.expire("hello", 10);
            Assertions.assertEquals("world", jedis.get("hello"));
            jedis.del("hello");
        }
    }

    @Test
    void testList() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush("list", "1");
            jedis.lpush("list", "2");
            jedis.lpush("list", "3");
            Assertions.assertEquals("3", jedis.lpop("list"));
            Assertions.assertEquals("2", jedis.lpop("list"));
            Assertions.assertEquals("1", jedis.lpop("list"));
        }
    }

    @Test
    void testHash() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset("hash", "1", "1");
            jedis.hset("hash", "2", "2");
            jedis.hset("hash", "3", "3");
            Assertions.assertEquals("1", jedis.hget("hash", "1"));
            Assertions.assertEquals("2", jedis.hget("hash", "2"));
            Assertions.assertEquals("3", jedis.hget("hash", "3"));
        }
    }

    @Test
    void testSet() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd("set", "1");
            jedis.sadd("set", "2");
            jedis.sadd("set", "3");
            Assertions.assertTrue(jedis.sismember("set", "1"));
            Assertions.assertTrue(jedis.sismember("set", "2"));
            Assertions.assertTrue(jedis.sismember("set", "3"));
        }
    }

    @Test
    void testZSet() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.zadd("zset", 1, "1");
            jedis.zadd("zset", 2, "2");
            jedis.zadd("zset", 3, "3");
            Assertions.assertEquals(1, jedis.zscore("zset", "1"));
            Assertions.assertEquals(2, jedis.zscore("zset", "2"));
            Assertions.assertEquals(3, jedis.zscore("zset", "3"));
        }
    }

}
