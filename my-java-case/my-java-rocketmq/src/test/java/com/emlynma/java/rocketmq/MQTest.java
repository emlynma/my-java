package com.emlynma.java.rocketmq;

import org.junit.jupiter.api.Test;

public class MQTest {

    @Test
    public void testProducer() throws Exception {
        TestProducer.produce();
    }

    @Test
    public void testConsumer() throws Exception {
        TestConsumer.consume();
    }

}
