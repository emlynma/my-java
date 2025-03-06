package com.emlynma.java.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
public class TestConsumer {

    public static void consume() throws Exception {
        ClientServiceProvider provider = ClientServiceProvider.loadService();

        String endpoints = "192.168.10.209:8081";

        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .build();

        String tag = "*";

        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);

        String consumerGroup = "test_group";

        String topic = "test_topic";

        PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                // 设置消费者分组。
                .setConsumerGroup(consumerGroup)
                // 设置预绑定的订阅关系。
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                // 设置消费监听器。
                .setMessageListener(messageView -> {
                    // 处理消息并返回消费结果。
                    System.out.println("Consume message successfully, messageId=" + messageView.getMessageId());
                    System.out.println("Consume message successfully, messageBody=" + StandardCharsets.UTF_8.decode(messageView.getBody()));
                    return ConsumeResult.SUCCESS;
                })
                .build();
        Thread.sleep(Long.MAX_VALUE);
        // 如果不需要再使用 PushConsumer，可关闭该实例。
        pushConsumer.close();
    }

}
