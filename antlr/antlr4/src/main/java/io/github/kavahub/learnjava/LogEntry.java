package io.github.kavahub.learnjava;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 日志体
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
public class LogEntry {
    private LogLevel level;
    private String message;
    private LocalDateTime timestamp;

}
