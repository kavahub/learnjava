package io.github.kavahub.learnjava.concurrent;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.google.common.util.concurrent.RateLimiter;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link RateLimiter} 使用的是一种叫令牌桶的流控算法，<code>RateLimiter</code> 会按照一定的频率往桶里扔令牌，
 * 线程拿到令牌才能执行，比如你希望自己的应用程序QPS不要超过1000，那么<code>RateLimiter</code> 设置1000的速率后，
 * 就会每秒往桶里扔1000个令牌。
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RateLimiterLongRunningTest {
    @Test
    public void givenLimitedResource_whenUseRateLimiter_thenShouldLimitPermits() {
        // given
        RateLimiter rateLimiter = RateLimiter.create(100);

        // when
        long startTime = ZonedDateTime.now().getSecond();
        IntStream.range(0, 1000).forEach(i -> {
            rateLimiter.acquire();
            doSomeLimitedOperation();
        });
        long elapsedTimeSeconds = ZonedDateTime.now().getSecond() - startTime;

        // then
        assertThat(elapsedTimeSeconds >= 10);
    }

    @Test
    public void givenLimitedResource_whenRequestTwice_thenShouldPermitWithoutBlocking() {
        // given
        RateLimiter rateLimiter = RateLimiter.create(2);

        // when
        long startTime = ZonedDateTime.now().getSecond();
        rateLimiter.acquire(1);
        doSomeLimitedOperation();
        rateLimiter.acquire(1);
        doSomeLimitedOperation();
        long elapsedTimeSeconds = ZonedDateTime.now().getSecond() - startTime;

        // then
        assertThat(elapsedTimeSeconds <= 1);
    }

    @Test
    public void givenLimitedResource_whenRequestOnce_thenShouldPermitWithoutBlocking() {
        // given
        RateLimiter rateLimiter = RateLimiter.create(100);

        // when
        long startTime = ZonedDateTime.now().getSecond();
        rateLimiter.acquire(100);
        doSomeLimitedOperation();
        long elapsedTimeSeconds = ZonedDateTime.now().getSecond() - startTime;

        // then
        assertThat(elapsedTimeSeconds <= 1);
    }

    @Test
    public void givenLimitedResource_whenTryAcquire_shouldNotBlockIndefinitely() {
        // given
        RateLimiter rateLimiter = RateLimiter.create(1);

        // when
        rateLimiter.acquire();
        boolean result = rateLimiter.tryAcquire(2, 10, TimeUnit.MILLISECONDS);

        // then
        assertThat(result).isFalse();

    }

    private void doSomeLimitedOperation() {
        // some computing
    }

}
