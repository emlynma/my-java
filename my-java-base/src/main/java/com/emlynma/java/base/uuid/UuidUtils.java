package com.emlynma.java.base.uuid;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.NameBasedGenerator;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

public abstract class UuidUtils {

    private static final TimeBasedGenerator TIME_BASED_GENERATOR;
    private static final NameBasedGenerator NAME_BASED_GENERATOR;
    private static final RandomBasedGenerator RANDOM_BASED_GENERATOR;

    static {
        TIME_BASED_GENERATOR = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        NAME_BASED_GENERATOR = Generators.nameBasedGenerator(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        RANDOM_BASED_GENERATOR = Generators.randomBasedGenerator();
    }

    /**
     * 生成版本 1 UUID（基于时间戳和MAC节点）
     */
    public static String generateTimeBasedUUID() {
        return TIME_BASED_GENERATOR.generate().toString();
    }

    /**
     * 生成版本 5 UUID（基于名称和命名空间的 SHA-1 散列）
     */
    public static String generateNameBasedUUID(String name) {
        return NAME_BASED_GENERATOR.generate(name).toString();
    }

    /**
     * 生成版本 4 UUID（基于随机数）
     */
    public static String generateRandomBasedUUID() {
        return RANDOM_BASED_GENERATOR.generate().toString();
    }

}
