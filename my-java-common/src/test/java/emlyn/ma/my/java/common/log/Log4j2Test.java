package emlyn.ma.my.java.common.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class Log4j2Test {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void testLog() {
        // ALL
        LOGGER.trace("test trace");
        LOGGER.debug("test debug");
        LOGGER.info("test info");
        LOGGER.warn("test warn");
        LOGGER.error("test error");
        LOGGER.fatal("test fatal");
        // OFF
        System.out.println(LOGGER.getName());
    }

}
