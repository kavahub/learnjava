package io.github.kavahub.learnjava;

import java.util.List;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

/**
 * 字符串
 */
@Slf4j
public class CompactStringExample {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();

    List<String> strings = IntStream
        // rangeClosed()返回类顺序排列IntStream从startInclusive到endInclusive通过的1递增步长这既包括startInclusive和endInclusive值，而range()不包含
        .rangeClosed(1, 10_000_000)
        // 转换成对象
        .mapToObj(Integer::toString)
        // 会和集合
        .collect(toList());
    long totalTime = System.currentTimeMillis() - startTime;
    log.info("Generated {} strings in {} ms.", strings.size(), totalTime);

    startTime = System.currentTimeMillis();
    String appended = strings.stream()
        // 截断为给定的最大长度, 包含maxSize
        .limit(100)
        // 聚合求值, identity 是初始值。identity与第一个聚合，然后第二个值再聚合
        .reduce("", (left, right) -> left.toString() + right.toString());    
    totalTime = System.currentTimeMillis() - startTime;
    log.info("Created string of length {} in {} ms.", appended.length(), totalTime );

    log.info("reduce: {}",  appended);
  }
}
