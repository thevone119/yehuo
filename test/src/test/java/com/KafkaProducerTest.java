package com;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author huangtw
 * @data 2020/12/28 17:44
 *
 **/
public class KafkaProducerTest {
    private static Properties kafkaProps;

    private static void initKafka() {
        kafkaProps = new Properties();
        // broker url
        kafkaProps.put("bootstrap.servers", "192.168.72.130:9092,192.168.72.131:9092,192.168.72.132:9092"); //,192.168.216.139:9092,192.168.216.140:9092
        // request need to validate
        kafkaProps.put("acks", "all");
        // request failed to try
        kafkaProps.put("retries", 0);
        // memory cache size
        kafkaProps.put("batch.size", 16384);
        //
        kafkaProps.put("linger.ms", 1);
        kafkaProps.put("buffer.memory", 33554432);
        // define the way of key and value serializer
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    }

    public static void main(String[] args) {
        initKafka();
        Producer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);
        for (int i = 0; i < 10000; i++) {
            producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
        }
        System.out.println("Message sent successfully!");
        producer.close();
    }


}
