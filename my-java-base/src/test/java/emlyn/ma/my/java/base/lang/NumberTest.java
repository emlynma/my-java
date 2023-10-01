package emlyn.ma.my.java.base.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberTest {

    @Test
    void testNumber() {
        Number n;
        System.out.println(n = 1000000000);
        System.out.println(n = 1000000000000000000L);
        System.out.println(n = 1000000000.0F);
        System.out.println(n = 1000000000000000000D);
    }

    @Test
    void testInteger() {
        Integer i = Integer.valueOf("1000000000");
        Assertions.assertEquals(1000000000, i);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        Assertions.assertTrue(127 == Integer.valueOf(127)); // 自动拆箱
        Assertions.assertTrue(Integer.valueOf(127) == Integer.valueOf(127)); // 有缓存 -128 - 127
        Assertions.assertTrue(128 == Integer.valueOf(128)); // 自动拆箱
        Assertions.assertFalse(Integer.valueOf(128) == Integer.valueOf(128)); // 无缓存 -128 - 127
        Assertions.assertTrue(10086 == Integer.valueOf(10086)); // 自动拆箱
        Assertions.assertTrue(10086 == Integer.valueOf(10086).intValue()); // 显式拆箱
    }

    @Test
    void testDouble() {
        Double d = Double.valueOf("100.01234500");
        System.out.println(d);
        System.out.println(d.intValue());
        System.out.format("%.2f", d);
    }

}
