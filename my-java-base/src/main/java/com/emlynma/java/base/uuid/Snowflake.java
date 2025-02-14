package com.emlynma.java.base.uuid;

/**
 * Snowflake 算法用来生成64位的ID，刚好可以用long整型存储，能够用于分布式系统中生产唯一的ID， 并且生成的ID有大致的顺序。
 * 在这次实现中，生成的64位ID可以分成5个部分：0 - 41位时间戳 - 5位数据中心标识 - 5位机器标识 - 12位序列号
 * 5位数据中心标识跟5位机器标识这样的分配仅仅是当前实现中分配的，如果业务有其实的需要，可以按其它的分配比例分配，如10位机器标识，不需要数据中心标识。
 */
public class Snowflake {

    // 起始时间戳：2023-01-01 00:00:00
    private static final long START_TIMESTAMP = 1672502400000L;

    // 数据中心标识位数
    private static final long CENTER_ID_BITS = 5L;
    // 机器标识位数
    private static final long WORKER_ID_BITS = 5L;
    // 序列号位数
    private static final long SEQUENCE_BITS = 12L;

    // 数据中心标识最大值
    private static final long MAX_CENTER_ID = ~(-1L << CENTER_ID_BITS);
    // 机器标识最大值
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    // 序列号最大值
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    // 数据中心标识左移位数
    private static final long CENTER_ID_SHIFT = SEQUENCE_BITS;
    // 机器标识左移位数
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS + CENTER_ID_BITS;
    // 时间戳左移位数
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + CENTER_ID_BITS + WORKER_ID_BITS;

    private final long centerId;
    private final long workerId;

    private long sequence = 0L;
    private long lastTimestamp = 0L;

    public Snowflake(long centerId, long workerId) {
        if (centerId < 0 || centerId > MAX_CENTER_ID) {
            throw new IllegalArgumentException("centerId can't be greater than " + MAX_CENTER_ID + " or less than 0");
        }
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("workerId can't be greater than " + MAX_WORKER_ID + " or less than 0");
        }
        this.centerId = centerId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long currTimestamp = System.currentTimeMillis();
        if (currTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id.");
        } else if (currTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                currTimestamp = nextTimestamp();
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = currTimestamp;
        return ((currTimestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT)
                | (centerId << CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    public long nextTimestamp() {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
