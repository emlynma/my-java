package com.emlyn.ma.common.lock;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPooled;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class RedisLock implements Lock {

    private static final ThreadLocal<String> LOCAL_LOCK_TOKEN = new ThreadLocal<>();
    private static final long SLEEP_TIME = 20;
    private static final long DEFAULT_EXPIRE_TIME = 1000 * 60;
    private static final String LOCK_PREFIX = "lock:";
    private static final String LOCK_LUA_SCRIPT = "if redis.call('set', KEYS[1], ARGV[1], 'nx', 'px', ARGV[2]) then return 1 else return 0 end";
    private static final String UNLOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private final String lockKey;
    private final long expireTime;

    private final JedisPooled jedis = new JedisPooled("localhost", 6379);

    public RedisLock(String lockKey) {
        this.lockKey = lockKey;
        this.expireTime = DEFAULT_EXPIRE_TIME;
    }

    public RedisLock(String lockKey, long expireTime, TimeUnit unit) {
        this.lockKey = lockKey;
        this.expireTime = unit.toMillis(expireTime);
    }

    @Override
    public boolean tryLock() {
        String redisKey = LOCK_PREFIX + lockKey;
        String redisValue = UUID.randomUUID().toString();
        Long result;
        try {
            result = (Long) jedis.eval(LOCK_LUA_SCRIPT, 1, redisKey, redisValue, String.valueOf(expireTime));
        } catch (Exception e) {
            throw new RuntimeException("tryLock error");
        }
        if (result == 1) {
            LOCAL_LOCK_TOKEN.set(redisValue);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        long remainTime = unit.toMillis(time);
        long endTime = System.currentTimeMillis() + remainTime;
        while (remainTime > 0) {
            if (tryLock()) {
                return true;
            }
            try {
                Thread.sleep(Math.min(remainTime, SLEEP_TIME));
            } catch (InterruptedException e) {
                throw new RuntimeException("tryLock error");
            }
            remainTime = endTime - System.currentTimeMillis();
        }
        return false;
    }

    @Override
    public void lock() {
        while (true) {
            if (tryLock()) {
                return;
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException("lock error");
            }
        }
    }

    @Override
    public void unlock() {
        String redisKey = LOCK_PREFIX + lockKey;
        String redisValue = LOCAL_LOCK_TOKEN.get();
        Long result;
        try {
            result = (Long) jedis.eval(UNLOCK_LUA_SCRIPT, 1, redisKey, redisValue);
        } catch (Exception e) {
            throw new RuntimeException("unlock error");
        } finally {
            LOCAL_LOCK_TOKEN.remove();
        }
        if (result == 0) {
            log.warn("unlock failed");
        }
    }

    @Override
    public void lockInterruptibly() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

}
