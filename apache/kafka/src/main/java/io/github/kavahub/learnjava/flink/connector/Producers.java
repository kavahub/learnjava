package io.github.kavahub.learnjava.flink.connector;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import io.github.kavahub.learnjava.flink.model.Backup;
import io.github.kavahub.learnjava.flink.schema.BackupSerializationSchema;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class Producers {
    public static FlinkKafkaProducer011<String> createStringProducer(String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<>(kafkaAddress, topic, new SimpleStringSchema());
    }

    public static FlinkKafkaProducer011<Backup> createBackupProducer(String topic, String kafkaAddress) {
        return new FlinkKafkaProducer011<Backup>(kafkaAddress, topic, new BackupSerializationSchema());
    }
}
