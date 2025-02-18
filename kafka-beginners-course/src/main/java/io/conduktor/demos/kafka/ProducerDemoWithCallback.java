package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ObjectName;
import java.util.Properties;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ProducerDemoWithCallback {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

    public static void main(String[] args) {

        log.info("Start Producer");

        Properties props = new Properties();

        props.setProperty("bootstrap.servers", "127.0.0.1:29092");

        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());

        props.setProperty("batch.size", String.valueOf(400));
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for (int j = 0; j < 3; j++) {

            ProducerRecord<String, String> record = new ProducerRecord<String, String>("demo_callback", StringGenerator.getSaltString());

            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e == null) {
                        log.info("Successfully sent record \n" +
                                "Topic: " +metadata.topic()  + "\n" +
                                "Partition: " +metadata.partition()  + "\n" +
                                "Offset: " +metadata.offset()  + "\n" +
                                "Timestamp: " +metadata.timestamp());
                    } else {
                        log.error("Error while sending record", e);
                    }
                }
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        producer.flush();

        producer.close();
    }
}