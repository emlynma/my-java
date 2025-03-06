package com.emlynma.java.rocketmq;

import com.emlynma.java.base.json.JsonUtils;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;

public class TestProducer {

    public static void produce() throws Exception {
        String endpoint = "192.168.10.209:8080";

        String topic = "test_topic";

        ClientServiceProvider provider = ClientServiceProvider.loadService();

        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);

        ClientConfiguration configuration = builder.build();

        Producer producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();

        Message message = provider.newMessageBuilder()
                .setTopic(topic)
                .setKeys("messageKey")
                .setTag("tag_v1")
                .setBody(JsonUtils.toJson(new MsgInfo("123", "name", "data")).getBytes())
                .build();

        SendReceipt sendReceipt = producer.send(message);

        System.out.println("send success, message_id: " + sendReceipt.getMessageId());

        producer.close();
    }

}
