package com.emlyn.ma.base.design;

import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.util.ServiceLoader;

public class SPITest {

    @Test
    public void testSPI() {
        ServiceLoader<Driver> drivers = ServiceLoader.load(Driver.class);
        for (Driver driver : drivers) {
            System.out.println("driver: " + driver.getClass() + ", loader: " + driver.getClass().getClassLoader());
        }
    }

}
