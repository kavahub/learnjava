package io.github.kavahub.learnjava.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

/**
 * 
 * 是否全字母字符串
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class Pangram {
    private static final int ALPHABET_COUNT = 26;

    public boolean isPangram(String str) {
        if (str == null)
            return false;
        Boolean[] alphabetMarker = new Boolean[ALPHABET_COUNT];
        Arrays.fill(alphabetMarker, false);

        int alphabetIndex = 0;
        String strUpper = str.toUpperCase();
        for (int i = 0; i < str.length(); i++) {
            if ('A' <= strUpper.charAt(i) && strUpper.charAt(i) <= 'Z') {
                alphabetIndex = strUpper.charAt(i) - 'A';
                alphabetMarker[alphabetIndex] = true;
            }
        }
        for (boolean index : alphabetMarker) {
            if (!index)
                return false;
        }
        return true;
    }

    public boolean isPangramWithStreams(String str) {
        if (str == null)
            return false;

        // filtered character stream
        String strUpper = str.toUpperCase();
        Stream<Character> filteredCharStream = strUpper.chars().filter(item -> ((item >= 'A' && item <= 'Z')))
                .mapToObj(c -> (char) c);
        Map<Character, Boolean> alphabetMap = filteredCharStream
                .collect(
                    // 参数含义分别是：
                    // keyMapper：Key 的映射函数
                    // valueMapper：Value 的映射函数
                    // mergeFunction：当 Key 冲突时，调用的合并方法
                    // mapSupplier：Map 构造器，在需要返回特定的 Map 时使用
                    Collectors.toMap(item -> item, k -> Boolean.TRUE, (p1, p2) -> p1)
                    );

        return (alphabetMap.size() == ALPHABET_COUNT);
    }

    public boolean isPerfectPangram(String str) {
        if (str == null)
            return false;

        // filtered character stream
        String strUpper = str.toUpperCase();
        Stream<Character> filteredCharStream = strUpper.chars().filter(item -> ((item >= 'A' && item <= 'Z')))
                .mapToObj(c -> (char) c);
        Map<Character, Long> alphabetFrequencyMap = filteredCharStream
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return (alphabetFrequencyMap.size() == ALPHABET_COUNT
                && alphabetFrequencyMap.values().stream().allMatch(item -> item == 1));
    }
}
