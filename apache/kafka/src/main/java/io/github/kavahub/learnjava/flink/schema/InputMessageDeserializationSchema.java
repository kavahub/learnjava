package io.github.kavahub.learnjava.flink.schema;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import io.github.kavahub.learnjava.flink.model.InputMessage;

/**
 * TODO
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */
public class InputMessageDeserializationSchema implements DeserializationSchema<InputMessage> {

    static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public InputMessage deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, InputMessage.class);
    }

    @Override
    public boolean isEndOfStream(InputMessage inputMessage) {
        return false;
    }

    @Override
    public TypeInformation<InputMessage> getProducedType() {
        return TypeInformation.of(InputMessage.class);
    }

}
