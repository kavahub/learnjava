package io.github.kavahub.learnjava;

import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * peek功能是消费流中的元素
 * 
 * <p>
 * 1. 存在此方法主要是为了支持调试，您需要在其中查看元素流过管道中特定点的情况
 * <p>
 * 2. 从Java 9开始，如果元素的数量预先已知并且在流中保持不变，则由于性能优化，将不会执行.peek()语句
 * <p>
 * 3. 在没有任何终端操作的情况下使用peek不会执行任何操作
 */
public class StreamPeekTest {
    private StringWriter out;

    @BeforeEach
    void setup() {
        out = new StringWriter();
    }

    @Test
    void givenStringStream_whenCallingPeekOnly_thenNoElementProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append);

        // then
        assertThat(out.toString()).isEmpty();
    }

    @Test
    void givenStringStream_whenCallingForEachOnly_thenElementsProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.forEach(out::append);

        // then
        assertThat(out.toString()).isEqualTo("AliceBobChuck");
    }

    @Test
    void givenStringStream_whenCallingPeekAndNoopForEach_thenElementsProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append).forEach(this::noop);

        // then
        assertThat(out.toString()).isEqualTo("AliceBobChuck");
    }

    @Test
    void givenStringStream_whenCallingPeekAndCollect_thenElementsProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append).collect(Collectors.toList());

        // then
        assertThat(out.toString()).isEqualTo("AliceBobChuck");
    }

    @Test
    void givenStringStream_whenCallingPeekAndForEach_thenElementsProcessedTwice() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append).forEach(out::append);

        // then
        // 从打印的信息可以看出，从流中取出一个元素，然后进行各种操作的
        assertThat(out.toString()).isEqualTo("AliceAliceBobBobChuckChuck");
    }

    @Test
    void givenStringStream_whenCallingPeek_thenElementsProcessedTwice() {
        // given
        Stream<User> userStream = Stream.of(new User("Alice"), new User("Bob"), new User("Chuck"));

        // when
        userStream.peek(u -> u.setName(u.getName().toLowerCase())).map(User::getName).forEach(out::append);

        // then
        assertThat(out.toString()).isEqualTo("alicebobchuck");
    }

    private static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    private void noop(String s) {
    }
}
