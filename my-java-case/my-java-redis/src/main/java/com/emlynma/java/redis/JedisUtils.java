package com.emlynma.java.redis;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
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

    public static void setex(String key, String value, int seconds) {
        jedis.setex(key, seconds, value);
    }

    /**
     * 设置分布式锁
     */
    public static boolean lock(String key, String value, int expireTime) {
        SetParams params = new SetParams().nx().px(expireTime);
        String result = jedis.set(key, value, params);
        return "OK".equals(result);
    }

    /**
     * 释放分布式锁
     */
    public static boolean unLock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        return result.equals(1L);
    }

}
