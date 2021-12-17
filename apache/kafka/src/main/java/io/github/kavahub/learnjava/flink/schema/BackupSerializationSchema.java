package io.github.kavahub.learnjava.flink.schema;

import org.apache.flink.api.common.serialization.SerializationSchema;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.github.kavahub.learnjava.flink.model.Backup;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Slf4j
public class BackupSerializationSchema  implements SerializationSchema<Backup> {

    static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public byte[] serialize(Backup backupMessage) {
        if (objectMapper == null) {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }
        try {
            String json = objectMapper.writeValueAsString(backupMessage);
            return json.getBytes();
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON", e);
        }
        return new byte[0];
    }
    
}
