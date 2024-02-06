package com.emlynma.java.base.util;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTests {

    @Test
    void testUUID() {
        // 获取一个版本1根据给定的时间的UUID。
        UUID uuid1 = UuidUtil.getTimeBasedUuid();
        System.out.println(uuid1);

        // 获取一个版本3根据给定的字节数组的UUID。
        UUID uuid3 = UUID.nameUUIDFromBytes("123".getBytes());
        System.out.println(uuid3);

        // 获取一个版本4根据随机字节数组的UUID。
        UUID uuid4 = UUID.randomUUID();
        System.out.println(uuid4);
    }

}
