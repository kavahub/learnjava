package io.github.kavahub.learnjava.kafka.producer;

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class CommonProducer {

    private final Producer<String, String> producer;

    public CommonProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    public Future<RecordMetadata> send(String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic_sports_news", key, value);
        return producer.send(record);
    }

    public void flush() {
        producer.flush();
    }

    public void beginTransaction() {
        producer.beginTransaction();
    }

    public void initTransaction() {
        producer.initTransactions();
    }

    public void commitTransaction() {
        producer.commitTransaction();
    }
    
}
