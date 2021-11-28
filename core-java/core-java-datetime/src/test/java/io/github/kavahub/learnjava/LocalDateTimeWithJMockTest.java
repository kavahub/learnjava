package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;

/**
 * 
 * {@link LocalDateTime} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Disabled("JMockit didn't get initialized")
public class LocalDateTimeWithJMockTest {
    @Test
    public void givenLocalDateTimeWithJMock_whenNow_thenGetFixedLocalDateTime() {
        Clock clock = Clock.fixed(Instant.parse("2014-12-21T10:15:30.00Z"), ZoneId.of("UTC"));

        new MockUp<LocalDateTime>() {
            @Mock
            public LocalDateTime now() {
                return LocalDateTime.now(clock);
            }
        };
        String dateTimeExpected = "2014-12-21T10:15:30";

        LocalDateTime now = LocalDateTime.now();

        assertThat(now).isEqualTo(dateTimeExpected);
    }

    @Test
    public void givenLocalDateTimeWithExpectations_whenNow_thenGetFixedLocalDateTime() {
        Clock clock = Clock.fixed(Instant.parse("2014-12-23T10:15:30.00Z"), ZoneId.of("UTC"));
        LocalDateTime dateTimeExpected = LocalDateTime.now(clock);
        new Expectations() {
            {
                LocalDateTime.now();
                result = dateTimeExpected;
            }
        };

        LocalDateTime now = LocalDateTime.now();

        assertThat(now).isEqualTo(dateTimeExpected);
    }
}
