package io.github.kavahub.learnjava.flink.operator;

import java.time.ZoneId;

import javax.annotation.Nullable;

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import io.github.kavahub.learnjava.flink.model.InputMessage;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class InputMessageTimestampAssigner implements AssignerWithPunctuatedWatermarks<InputMessage> {

    @Override
    public long extractTimestamp(InputMessage element, long previousElementTimestamp) {
        ZoneId zoneId = ZoneId.systemDefault();
        return element.getSentAt()
            .atZone(zoneId)
            .toEpochSecond() * 1000;
    }

    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(InputMessage lastElement, long extractedTimestamp) {
        return new Watermark(extractedTimestamp - 15);
    }
    
}
