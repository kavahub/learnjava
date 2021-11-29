package io.github.kavahub.learnjava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link StreamTokenizer} 使用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class StreamTokenizerExample {
    private static final String INPUT_FILE = "/stream-tokenizer-example.txt";
    private static final int QUOTE_CHARACTER = '\'';
    private static final int DOUBLE_QUOTE_CHARACTER = '"';

    public static List<Object> streamTokenizerWithDefaultConfiguration(Reader reader) throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
        List<Object> tokens = new ArrayList<>();

        int currentToken = streamTokenizer.nextToken();
        //  TT_EOF – 表示流结束的常量
        while (currentToken != StreamTokenizer.TT_EOF) {
            // TT_EOL – 表示行结束的常量
            // TT_NUMBER – 表示数字标记的常量
            // TT_WORD – 表示单词标记的常量
            if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                tokens.add(streamTokenizer.nval);
            } else if (streamTokenizer.ttype == StreamTokenizer.TT_WORD
                    || streamTokenizer.ttype == QUOTE_CHARACTER
                    || streamTokenizer.ttype == DOUBLE_QUOTE_CHARACTER) {
                tokens.add(streamTokenizer.sval);
            } else {
                tokens.add((char) currentToken);
            }

            currentToken = streamTokenizer.nextToken();
        }

        return tokens;
    }

    public static List<Object> streamTokenizerWithCustomConfiguration(Reader reader) throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
        List<Object> tokens = new ArrayList<>();

        streamTokenizer.wordChars('!', '-');
        // 普通字符处理
        streamTokenizer.ordinaryChar('/');
        // 注释行(不读取)，默认'/'
        streamTokenizer.commentChar('#');
        // 决定一个行结束符是否被当作一个基本的符号处理，如果是true，则被当作一个基本符号，不当作普通的分隔符，
        // 如果是false，则保持原义，即当作普通的分隔符
        streamTokenizer.eolIsSignificant(true);

        int currentToken = streamTokenizer.nextToken();
        //  TT_EOF – 表示流结束的常量
        while (currentToken != StreamTokenizer.TT_EOF) {
            // TT_EOL – 表示行结束的常量
            // TT_NUMBER – 表示数字标记的常量
            // TT_WORD – 表示单词标记的常量
            if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                tokens.add(streamTokenizer.nval);
            } else if (streamTokenizer.ttype == StreamTokenizer.TT_WORD
                    || streamTokenizer.ttype == QUOTE_CHARACTER
                    || streamTokenizer.ttype == DOUBLE_QUOTE_CHARACTER) {
                tokens.add(streamTokenizer.sval);
            } else {
                tokens.add((char) currentToken);
            }
            currentToken = streamTokenizer.nextToken();
        }

        return tokens;
    }

    public static Reader createReaderFromFile() throws FileNotFoundException {
        String inputFile = StreamTokenizerExample.class.getResource(INPUT_FILE).getFile();
        return new FileReader(inputFile);
    }

    public static void main(String[] args) throws IOException {
        List<Object> tokens1 = streamTokenizerWithDefaultConfiguration(createReaderFromFile());
        List<Object> tokens2 = streamTokenizerWithCustomConfiguration(createReaderFromFile());

        log.info(tokens1.toString());
        log.info(tokens2.toString());
    }    
}
