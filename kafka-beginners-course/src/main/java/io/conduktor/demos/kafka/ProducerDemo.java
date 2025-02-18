package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ProducerDemo {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class);

    public static void main(String[] args) {

        log.info("Start Producer");

        Properties props = new Properties();

        props.setProperty("bootstrap.servers", "127.0.0.1:29092");

        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<String, String>("demo", "hello worlddd");

        producer.send(record);

        producer.flush();

        producer.close();
    }
}