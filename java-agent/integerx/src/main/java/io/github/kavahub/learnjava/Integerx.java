package io.github.kavahub.learnjava;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

/**
 * 检查增强后的功能
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class Integerx {

  /**
   * 打印所有私有属性
   */
  public void printIntegerFields() {
    Field[] fields = Integer.class.getDeclaredFields();
    log.info("Integer fields - {} - [{}]", fields.length,
        Stream.of(fields).map(Field::getName).collect(Collectors.joining(",")));
  }
}
