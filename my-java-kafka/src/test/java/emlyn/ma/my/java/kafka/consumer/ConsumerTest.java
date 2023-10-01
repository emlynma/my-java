package emlyn.ma.my.java.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerTest {

    private static Consumer<String, String> consumer;
    private static ExecutorService executorService;

    @BeforeAll
    static void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);

        executorService = Executors.newFixedThreadPool(3);
    }

    @AfterAll
    static void close() {
        consumer.close();
    }

    @Test
    void testConsumer() {
        consumer.subscribe(List.of("my_test_topic"));
        LocalDateTime after5s = LocalDateTime.now().plusSeconds(5);
        while (LocalDateTime.now().isBefore(after5s)) {
            System.out.println("start polling...");
            consumer.poll(Duration.ofSeconds(1)).forEach(record -> {
                System.out.println("topic: " + record.topic());
                System.out.println("partition: " + record.partition());
                System.out.println("offset: " + record.offset());
                System.out.println("timestamp: " + record.timestamp());
                System.out.println("key: " + record.key());
                System.out.println("value: " + record.value());
            });
        }
    }

    @Test
    void testConsumerMultiThread() {
        Runnable consumerTask = () -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "my-test-group");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<Object, Object> currConsumer = new KafkaConsumer<>(props);
            currConsumer.subscribe(List.of("my_test_topic"));
            LocalDateTime after5s = LocalDateTime.now().plusSeconds(10);
            while (LocalDateTime.now().isBefore(after5s)) {
                System.out.println(Thread.currentThread().getName() + ": start polling...");
                currConsumer.poll(Duration.ofSeconds(1)).forEach(record -> {
                    System.out.println(Thread.currentThread().getName() + ": topic: " + record.topic());
                    System.out.println(Thread.currentThread().getName() + ": partition: " + record.partition());
                    System.out.println(Thread.currentThread().getName() + ": offset: " + record.offset());
                    System.out.println(Thread.currentThread().getName() + ": timestamp: " + record.timestamp());
                    System.out.println(Thread.currentThread().getName() + ": key: " + record.key());
                    System.out.println(Thread.currentThread().getName() + ": value: " + record.value());
                });
            }
        };

        executorService.execute(consumerTask);
        executorService.execute(consumerTask);

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
