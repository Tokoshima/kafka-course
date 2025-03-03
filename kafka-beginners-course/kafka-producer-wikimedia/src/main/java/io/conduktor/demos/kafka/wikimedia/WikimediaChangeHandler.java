package io.conduktor.demos.kafka.wikimedia;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaChangeHandler implements BackgroundEventHandler {

    KafkaProducer<String, String> producer;
    String topic;
    private final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class.getSimpleName());

    public WikimediaChangeHandler(KafkaProducer<String, String> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
    }
    @Override
    public void onOpen() {
        System.out.println("Connection opened");
    }

    @Override
    public void onMessage(String event, MessageEvent message) {
        System.out.println("Event: " + event);
        System.out.println("Data: " + message.getData());
        producer.send(new ProducerRecord<String, String>(topic, message.getData()));
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @Override
    public void onComment(String comment) {
        System.out.println("Comment: " + comment);
    }

    @Override
    public void onClosed() {
        producer.close();
        System.out.println("Connection closed");
    }
}
