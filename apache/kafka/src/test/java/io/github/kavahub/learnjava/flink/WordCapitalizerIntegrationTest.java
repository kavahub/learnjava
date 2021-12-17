package io.github.kavahub.learnjava.flink;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.flink.operator.WordsCapitalizer;

/**
 * TODO
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */
public class WordCapitalizerIntegrationTest {

    @Test
    public void givenDataSet_whenExecuteWordCapitalizer_thenReturnCapitalizedWords() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        List<String> data = Arrays.asList("dog", "cat", "wolf", "pig");

        DataSet<String> testDataSet = env.fromCollection(data);

        List<String> dataProcessed = testDataSet
                .map(new WordsCapitalizer())
                .collect();

        List<String> testDataCapitalized = data.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        assertEquals(testDataCapitalized, dataProcessed);
    }
}
