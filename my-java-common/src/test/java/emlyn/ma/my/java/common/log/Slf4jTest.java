package emlyn.ma.my.java.common.log;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);

    @Test
    public void testLog() {
        // ALL
        LOGGER.trace("test trace");
        LOGGER.debug("test debug");
        LOGGER.info("test info");
        LOGGER.warn("test warn");
        LOGGER.error("test error");
        // OFF
        System.out.println(LOGGER.getName());
    }

}
