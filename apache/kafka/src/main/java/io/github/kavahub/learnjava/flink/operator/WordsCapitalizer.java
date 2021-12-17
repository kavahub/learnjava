package io.github.kavahub.learnjava.flink.operator;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class WordsCapitalizer implements MapFunction<String, String> {

    @Override
    public String map(String s) {
        return s.toUpperCase();
    }
    
}
