package com.emlynma.java.common.uuid;

import com.emlynma.java.common.uuid.Snowflake;
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
