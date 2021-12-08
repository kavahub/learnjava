package io.github.kavahub.learnjava;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.github.kavahub.learnjava.antlr.LogBaseListener;
import io.github.kavahub.learnjava.antlr.LogParser;

/**
 * 日志监听
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class LogListener extends LogBaseListener {

    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);

    private List<LogEntry> entries = new ArrayList<>();
    private LogEntry currentLogEntry;

    @Override
    public void enterEntry(LogParser.EntryContext ctx) {
        this.currentLogEntry = new LogEntry();
    }

    @Override
    public void exitEntry(LogParser.EntryContext ctx) {
        entries.add(currentLogEntry);
    }

    @Override
    public void enterTimestamp(LogParser.TimestampContext ctx) {
        currentLogEntry.setTimestamp(LocalDateTime.parse(ctx.getText(), DEFAULT_DATETIME_FORMATTER));
    }

    @Override
    public void enterMessage(LogParser.MessageContext ctx) {
        currentLogEntry.setMessage(ctx.getText());
    }

    @Override
    public void enterLevel(LogParser.LevelContext ctx) {
        currentLogEntry.setLevel(LogLevel.valueOf(ctx.getText()));
    }

    public List<LogEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
    
}
