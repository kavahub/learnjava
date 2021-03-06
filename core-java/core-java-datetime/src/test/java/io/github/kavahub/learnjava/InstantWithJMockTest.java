package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit5.JMockitExtension;

/**
 * 
 * {@link Instant} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Disabled("JMockit didn't get initialized")
@ExtendWith(JMockitExtension.class)
public class InstantWithJMockTest {

    @Test
    public void givenInstantWithJMock_whenNow_thenGetFixedInstant() {
        String instantExpected = "2014-12-21T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        new MockUp<Instant>() {
            @Mock
            public Instant now() {
                return Instant.now(clock);
            }
        };

        Instant now = Instant.now();

        assertThat(now.toString()).isEqualTo(instantExpected);
    }

    @Test
    public void givenInstantWithExpectations_whenNow_thenGetFixedInstant() {
        Clock clock = Clock.fixed(Instant.parse("2014-12-23T10:15:30.00Z"), ZoneId.of("UTC"));
        Instant instantExpected = Instant.now(clock);
        new Expectations(Instant.class) {
            {
                Instant.now();
                result = instantExpected;
            }
        };

        Instant now = Instant.now();

        assertThat(now).isEqualTo(instantExpected);
    }
}
