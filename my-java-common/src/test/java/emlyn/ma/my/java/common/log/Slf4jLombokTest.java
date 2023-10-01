package emlyn.ma.my.java.common.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class Slf4jLombokTest {

    @Test
    public void testLog() {
        // ALL
        log.trace("test trace");
        log.debug("test debug");
        log.info("test info");
        log.warn("test warn");
        log.error("test error");
        // OFF
        System.out.println(log.getName());
    }

}
