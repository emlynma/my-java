package emlyn.ma.my.java.common.log;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class JULTest {

    private static final Logger LOGGER = Logger.getLogger(JULTest.class.getName());

    @Test
    public void testLog() {
        // ALL
        LOGGER.finest("test finest");
        LOGGER.finer("test finer");
        LOGGER.fine("test fine");
        LOGGER.config("test config");
        LOGGER.info("test info");
        LOGGER.warning("test warning");
        LOGGER.severe("test severe");
        // OFF
        System.out.println(LOGGER.getName());
    }

}
