package io.github.kavahub.learnjava.flink;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.flink.model.Backup;
import io.github.kavahub.learnjava.flink.model.InputMessage;
import io.github.kavahub.learnjava.flink.operator.BackupAggregator;
import io.github.kavahub.learnjava.flink.operator.InputMessageTimestampAssigner;
import io.github.kavahub.learnjava.flink.schema.BackupSerializationSchema;
import io.github.kavahub.learnjava.flink.schema.InputMessageDeserializationSchema;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class BackupCreatorIntegrationTest {
    public static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    public void givenProperJson_whenDeserializeIsInvoked_thenProperObjectIsReturned() throws IOException {
        InputMessage message = new InputMessage("Me", "User", LocalDateTime.now(), "Test Message");
        byte[] messageSerialized = mapper.writeValueAsBytes(message);
        DeserializationSchema<InputMessage> deserializationSchema = new InputMessageDeserializationSchema();
        InputMessage messageDeserialized = deserializationSchema.deserialize(messageSerialized);

        assertEquals(message, messageDeserialized);
    }

    @Test
    public void givenMultipleInputMessagesFromDifferentDays_whenBackupCreatorIsUser_thenMessagesAreGroupedProperly() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        InputMessage message = new InputMessage("Me", "User", currentTime, "First TestMessage");
        InputMessage secondMessage = new InputMessage("Me", "User", currentTime.plusHours(1), "First TestMessage");
        InputMessage thirdMessage = new InputMessage("Me", "User", currentTime.plusHours(2), "First TestMessage");
        InputMessage fourthMessage = new InputMessage("Me", "User", currentTime.plusHours(3), "First TestMessage");
        InputMessage fifthMessage = new InputMessage("Me", "User", currentTime.plusHours(25), "First TestMessage");
        InputMessage sixthMessage = new InputMessage("Me", "User", currentTime.plusHours(26), "First TestMessage");

        List<InputMessage> firstBackupMessages = Arrays.asList(message, secondMessage, thirdMessage, fourthMessage);
        List<InputMessage> secondBackupMessages = Arrays.asList(fifthMessage, sixthMessage);
        List<InputMessage> inputMessages = new ArrayList<>();
        inputMessages.addAll(firstBackupMessages);
        inputMessages.addAll(secondBackupMessages);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);
        DataStreamSource<InputMessage> testDataSet = env.fromCollection(inputMessages);
        CollectingSink sink = new CollectingSink();
        testDataSet.assignTimestampsAndWatermarks(new InputMessageTimestampAssigner())
          .timeWindowAll(Time.hours(24))
          .aggregate(new BackupAggregator())
          .addSink(sink);

        env.execute();

        Awaitility.await().until(() ->  CollectingSink.backups.size() == 2);
        assertEquals(2, CollectingSink.backups.size());
        assertEquals(firstBackupMessages, CollectingSink.backups.get(0).getInputMessages());
        assertEquals(secondBackupMessages, CollectingSink.backups.get(1).getInputMessages());

    }

    @Test
    public void givenProperBackupObject_whenSerializeIsInvoked_thenObjectIsProperlySerialized() throws IOException {
        InputMessage message = new InputMessage("Me", "User", LocalDateTime.now(), "Test Message");
        List<InputMessage> messages = Arrays.asList(message);
        Backup backup = new Backup(messages, LocalDateTime.now());
        byte[] backupSerialized = mapper.writeValueAsBytes(backup);
        SerializationSchema<Backup> serializationSchema = new BackupSerializationSchema();
        byte[] backupProcessed = serializationSchema.serialize(backup);
        
        assertArrayEquals(backupSerialized, backupProcessed);
    }

    private static class CollectingSink implements SinkFunction<Backup> {
        
        public static List<Backup> backups = new ArrayList<>();

        @Override
        @SuppressWarnings("rawtypes")
        public void invoke(Backup value, Context context) throws Exception {
            backups.add(value);
        }
    }
}
