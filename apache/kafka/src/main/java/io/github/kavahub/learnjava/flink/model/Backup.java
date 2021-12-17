package io.github.kavahub.learnjava.flink.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class Backup {
    
    @JsonProperty("inputMessages")
    List<InputMessage> inputMessages;
    @JsonProperty("backupTimestamp")
    LocalDateTime backupTimestamp;
    @JsonProperty("uuid")
    UUID uuid;

    public Backup(List<InputMessage> inputMessages, LocalDateTime backupTimestamp) {
        this.inputMessages = inputMessages;
        this.backupTimestamp = backupTimestamp;
        this.uuid = UUID.randomUUID();
    }

    public List<InputMessage> getInputMessages() {
        return inputMessages;
    }
}
