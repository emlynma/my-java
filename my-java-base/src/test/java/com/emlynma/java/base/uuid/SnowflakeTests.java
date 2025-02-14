package com.emlynma.java.base.uuid;

import org.junit.jupiter.api.Test;

public class SnowflakeTests {

    @Test
    void test() throws InterruptedException {
        Snowflake snowflake = new Snowflake(1, 1);
        for (int i = 0; i < 100; i++) {
            System.out.println(snowflake.nextId());
            if (i % 10 == 0) {
                Thread.sleep(10);
            }
        }
    }

}
