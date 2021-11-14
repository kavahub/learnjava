package io.github.kavahub.learnjava;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InstantTest {
    @Test
    public void givenFixedClock_whenNow_thenGetFixedInstant() {
        String instantExpected = "2014-12-22T10:15:30Z";
        // fixed返回一个固定的时钟，总是给出相同的瞬间
        // 固定时钟的主要用例是在测试中，固定时钟确保测试不依赖于当前时钟
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));

        Instant instant = Instant.now(clock);

        assertThat(instant.toString()).isEqualTo(instantExpected);
    }
}
