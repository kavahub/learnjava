package io.github.kavahub.learnjava.flink;

import static io.github.kavahub.learnjava.flink.connector.Consumers.createInputMessageConsumer;
import static io.github.kavahub.learnjava.flink.connector.Consumers.createStringConsumerForTopic;
import static io.github.kavahub.learnjava.flink.connector.Producers.createBackupProducer;
import static io.github.kavahub.learnjava.flink.connector.Producers.createStringProducer;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import io.github.kavahub.learnjava.flink.model.Backup;
import io.github.kavahub.learnjava.flink.model.InputMessage;
import io.github.kavahub.learnjava.flink.operator.BackupAggregator;
import io.github.kavahub.learnjava.flink.operator.InputMessageTimestampAssigner;
import io.github.kavahub.learnjava.flink.operator.WordsCapitalizer;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class FlinkDataPipeline {
    public static void capitalize() throws Exception {
        String inputTopic = "flink_input";
        String outputTopic = "flink_output";
        String consumerGroup = "learnjava";
        String address = "localhost:9092";

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        FlinkKafkaConsumer011<String> flinkKafkaConsumer = createStringConsumerForTopic(inputTopic, address, consumerGroup);
        flinkKafkaConsumer.setStartFromEarliest();

        DataStream<String> stringInputStream = environment.addSource(flinkKafkaConsumer);

        FlinkKafkaProducer011<String> flinkKafkaProducer = createStringProducer(outputTopic, address);

        stringInputStream.map(new WordsCapitalizer())
            .addSink(flinkKafkaProducer);

        environment.execute();
    }

    public static void createBackup() throws Exception {
        String inputTopic = "flink_input";
        String outputTopic = "flink_output";
        String consumerGroup = "learnjava";
        String kafkaAddress = "localhost:9092";

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        FlinkKafkaConsumer011<InputMessage> flinkKafkaConsumer = createInputMessageConsumer(inputTopic, kafkaAddress, consumerGroup);
        flinkKafkaConsumer.setStartFromEarliest();

        flinkKafkaConsumer.assignTimestampsAndWatermarks(new InputMessageTimestampAssigner());
        FlinkKafkaProducer011<Backup> flinkKafkaProducer = createBackupProducer(outputTopic, kafkaAddress);

        DataStream<InputMessage> inputMessagesStream = environment.addSource(flinkKafkaConsumer);

        inputMessagesStream.timeWindowAll(Time.hours(24))
            .aggregate(new BackupAggregator())
            .addSink(flinkKafkaProducer);

        environment.execute();
    }

    public static void main(String[] args) throws Exception {
        createBackup();
    }
}
