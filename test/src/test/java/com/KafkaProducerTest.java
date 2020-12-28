package com;



import org.apache.kafka.clients.producer.KafkaProducer;
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
    private final KafkaProducer<String,String> producer;
    public final static String TOPIC = "test";

    private KafkaProducerTest(){
        Properties props = new Properties();
        // 服务器ip
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.43.83:9092,192.168.43.84:9092,192.168.43.62:9092");
        // 属性键值对都序列化成字符串
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //request.required.acks
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
        //props.put(ProducerConfig.ACKS_CONFIG,"-1");

        producer  = new KafkaProducer<>(props);
    }

    void produce() {
        int messageNo = 100;
        final int COUNT = 1000;

        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            producer.send(new ProducerRecord<String, String>(TOPIC, key ,data));
            System.out.println(data);
            messageNo ++;
        }
        producer.close();
    }


    @Test
    public void testKafkaProducer() throws IOException {
        new KafkaProducerTest().produce();
    }

    //http://www.open-open.com/lib/view/open1412991579999.html
    public static void main( String[] args )
    {
        new KafkaProducerTest().produce();
    }

}
