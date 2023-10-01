package emlyn.ma.my.java.base.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@SuppressWarnings("all")
public class NullPointerExceptionTest {

    @Test
    public void test() {
        Assertions.assertThrows(NullPointerException.class, () ->  {
            Integer a = 1, b = null;
            Integer c = add(a, b);
        });
    }

    private int add(int a, int b) {
        return a + b;
    }

    @Test
    void testOptional() {
        String str = null;
        String s1 = Optional.ofNullable(str)
                .filter(s -> s.length() > 0)
                .map(s -> s + "1")
                .orElse(null);
        System.out.println(s1);
    }

}
