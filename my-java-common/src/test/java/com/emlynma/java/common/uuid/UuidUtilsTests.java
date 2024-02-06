package com.emlynma.java.common.uuid;

import com.emlynma.java.common.uuid.UuidUtils;
import org.junit.jupiter.api.Test;

public class UuidUtilsTests {

    @Test
    void testGenerateTimeBasedUUID() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UuidUtils.generateTimeBasedUUID());
        }
    }

    @Test
    void testGenerateNameBasedUUID() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UuidUtils.generateNameBasedUUID("test"));
        }
    }

    @Test
    void testGenerateRandomBasedUUID() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UuidUtils.generateRandomBasedUUID());
        }
    }

}
