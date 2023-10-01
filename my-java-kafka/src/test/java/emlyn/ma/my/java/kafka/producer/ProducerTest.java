package emlyn.ma.my.java.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class ProducerTest {

    private static final String TOPIC = "my_test_topic";

    private static Producer<String, String> producer;

    @BeforeAll
    static void init() {
        // 配置Kafka生产者的属性
        Properties props = new Properties();
        // Kafka集群地址
        props.put("bootstrap.servers", "localhost:9092");
        // key的序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value的序列化方式
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 创建Kafka生产者
        producer = new KafkaProducer<>(props);
    }

    @AfterAll
    static void close() {
        producer.close();
    }

    @Test
    void testProducerIgnoreResult() {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "hello world");
        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProducerSync() {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "hello world");
        try {
            RecordMetadata recordMetadata = producer.send(record).get();
            Assertions.assertNotNull(recordMetadata);
            System.out.println("topic: " + recordMetadata.topic());
            System.out.println("partition: " + recordMetadata.partition());
            System.out.println("offset: " + recordMetadata.offset());
            System.out.println("timestamp: " + recordMetadata.timestamp());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProducerAsync() {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "hello world");
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("topic: " + metadata.topic());
                System.out.println("partition: " + metadata.partition());
                System.out.println("offset: " + metadata.offset());
                System.out.println("timestamp: " + metadata.timestamp());
            }
        });
    }

}
