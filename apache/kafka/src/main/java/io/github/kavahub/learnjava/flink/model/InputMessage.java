package io.github.kavahub.learnjava.flink.model;

import java.time.LocalDateTime;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Data
@AllArgsConstructor
@JsonSerialize
public class InputMessage {
    String sender;
    String recipient;
    LocalDateTime sentAt;
    String message; 
}
