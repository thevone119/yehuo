package com;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author huangtw
 * @data 2020/12/28 17:44
 **/
public class KafkaConsumerTest {
    private static Properties kafkaProps = new Properties();

    private static void kafkaInit() {
        kafkaProps.put("bootstrap.servers", "192.168.72.130:9092,192.168.72.131:9092,192.168.72.132:9092");
        // group id for each consumer
        kafkaProps.put("group.id", "test");
        // if value legal, auto add offset
        kafkaProps.put("enable.auto.commit", "true");
        // set how long time to udpate the offset value
        kafkaProps.put("auto.commit.interval.ms", "1000");
        // set session response time
        kafkaProps.put("session.timeout.ms", "30000");
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public static void main(String[] args) {
        kafkaInit();
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(kafkaProps);
        kafkaConsumer.subscribe(Collections.singletonList("my-topic"));
        System.out.println("Subscribed to topic:" + "my-topic");

        int i = 0;
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100)); // ?
            for (ConsumerRecord<String, String> record : records) {
                // print the offset, key and value for the consumer records
                System.out.printf("Offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }

    }
}
