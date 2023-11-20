package com.emlyn.ma.redis.jedis;

import redis.clients.jedis.JedisPooled;

public abstract class JedisUtils {

    private static final JedisPooled jedis;

    static {
        jedis = new JedisPooled("localhost", 6379);
    }

    public static String get(String key) {
        return jedis.get(key);
    }

    public static void set(String key, String value) {
        jedis.set(key, value);
    }

    public static void set(String key, String value, int seconds) {
        jedis.set(key, value);
        jedis.expire(key, seconds);
    }

}
