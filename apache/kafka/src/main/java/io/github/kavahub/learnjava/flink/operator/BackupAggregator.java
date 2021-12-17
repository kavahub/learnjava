package io.github.kavahub.learnjava.flink.operator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.flink.api.common.functions.AggregateFunction;

import io.github.kavahub.learnjava.flink.model.Backup;
import io.github.kavahub.learnjava.flink.model.InputMessage;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class BackupAggregator implements AggregateFunction<InputMessage, List<InputMessage>, Backup> {
    @Override
    public List<InputMessage> createAccumulator() {
        return new ArrayList<>();
    }

    @Override
    public List<InputMessage> add(InputMessage inputMessage, List<InputMessage> inputMessages) {
        inputMessages.add(inputMessage);
        return inputMessages;
    }

    @Override
    public Backup getResult(List<InputMessage> inputMessages) {
        Backup backup = new Backup(inputMessages, LocalDateTime.now());
        return backup;
    }

    @Override
    public List<InputMessage> merge(List<InputMessage> inputMessages, List<InputMessage> acc1) {
        inputMessages.addAll(acc1);
        return inputMessages;
    }
    
}
