package com.emlyn.ma.redis.jedis;

import redis.clients.jedis.JedisPooled;

import java.util.Properties;

public abstract class JedisUtils {

    private static final JedisPooled jedis;

    static {
        try {
            Properties redisProperties = new Properties();
            redisProperties.load(JedisUtils.class.getClassLoader().getResourceAsStream("redis.properties"));
            String host = redisProperties.getProperty("redis.host");
            int port = Integer.parseInt(redisProperties.getProperty("redis.port"));
            jedis = new JedisPooled(host, port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
