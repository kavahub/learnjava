package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link  Clock} 类用于使用时区提供对当前时刻, 日期和时间的访问
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
@Slf4j
public class ClockTest {
    @Test
	public void givenClock_withSytemUTC_retrievesInstant() {
		
		Clock clockUTC = Clock.systemUTC();

		assertEquals(clockUTC.getZone(), ZoneOffset.UTC);
		assertFalse(clockUTC.instant().equals(null));

		log.debug("UTC instant :: " + clockUTC.instant());
	}

	@Test
	public void givenClock_withSytem_retrievesInstant() {

		Clock clockSystem = Clock.system(ZoneId.of("Asia/Calcutta"));

		assertEquals(clockSystem.getZone(), ZoneId.of("Asia/Calcutta"));
		assertFalse(clockSystem.instant().equals(null));

		log.debug("System zone :: " + clockSystem.getZone());
		log.debug("instant :: " + clockSystem.instant());
	}

	@Test
	public void givenClock_withSytemDefaultZone_retrievesInstant() {
		
		Clock clockSystemDefault = Clock.systemDefaultZone();

		assertFalse(clockSystemDefault.getZone().equals(null));
		assertFalse(clockSystemDefault.instant().equals(null));

		log.debug("System Default zone :: " + clockSystemDefault.getZone());
		log.debug("System Default instant :: " + clockSystemDefault.instant());
	}

	@Test
	public void givenClock_withSytemUTC_retrievesTimeInMillis() {
		
		Clock clockMillis = Clock.systemDefaultZone();

		assertFalse(clockMillis.instant().equals(null));
		assertTrue(clockMillis.millis() > 0);

		log.debug("System Default millis :: " + clockMillis.millis());
	}

	@Test
	public void givenClock_usingOffset_retrievesFutureDate() {
		
		Clock baseClock = Clock.systemDefaultZone();

		// result clock will be later than baseClock
		Clock futureClock = Clock.offset(baseClock, Duration.ofHours(72));

		assertFalse(futureClock.instant().equals(null));
		assertTrue(futureClock.millis() > baseClock.millis());

		log.debug("Future Clock instant :: " + futureClock.instant());
	}

	@Test
	public void givenClock_usingOffset_retrievesPastDate() {
		Clock baseClock = Clock.systemDefaultZone();

		// result clock will be later than baseClock
		Clock pastClock = Clock.offset(baseClock, Duration.ofHours(-72));

		assertFalse(pastClock.instant().equals(null));
		assertTrue(pastClock.millis() < baseClock.millis());

		log.debug("Past Clock instant :: " + pastClock.instant());
	}

	@Test
	public void givenClock_usingTick_retrievesInstant() {
		Clock clockDefaultZone = Clock.systemDefaultZone();
        // tick方法返回一个时钟，该时钟返回从基本时钟开始的四舍五入到参数中指定
        // 持续时间的最接近值的时刻
		Clock clocktick = Clock.tick(clockDefaultZone, Duration.ofSeconds(300));

		assertEquals(clockDefaultZone.instant().equals(null), false);
		assertEquals(clocktick.instant().equals(null), false);
		assertTrue(clockDefaultZone.millis() > clocktick.millis());

		log.debug("Clock Default Zone instant : " + clockDefaultZone.instant());
		log.debug("Clock tick instant: " + clocktick.instant());
	}

	@Test
	public void givenClock_usingTickDurationNegative_throwsException() {
		
		Clock clockDefaultZone = Clock.systemDefaultZone();
        assertThrows(IllegalArgumentException.class, () -> Clock.tick(clockDefaultZone, Duration.ofSeconds(-300)));
	}
	
	@Test
	public void givenClock_usingTickSeconds_retrievesInstant() {
		ZoneId zoneId = ZoneId.of("Asia/Calcutta");
		Clock tickSeconds = Clock.tickSeconds(zoneId);

		assertEquals(tickSeconds.instant().equals(null), false);
		log.debug("Clock tick seconds instant :: " + tickSeconds.instant());

		tickSeconds = Clock.tick(Clock.system(ZoneId.of("Asia/Calcutta")), Duration.ofSeconds(100));
		assertEquals(tickSeconds.instant().equals(null), false);
	}

	@Test
	public void givenClock_usingTickMinutes_retrievesInstant() {
		
		Clock tickMinutes = Clock.tickMinutes(ZoneId.of("Asia/Calcutta"));

		assertEquals(tickMinutes.instant().equals(null), false);
		log.debug("Clock tick seconds instant :: " + tickMinutes.instant());

		tickMinutes = Clock.tick(Clock.system(ZoneId.of("Asia/Calcutta")), Duration.ofMinutes(5));
		assertEquals(tickMinutes.instant().equals(null), false);
	}

	@Test
	public void givenClock_usingWithZone_retrievesInstant() {
		
		ZoneId zoneSingapore = ZoneId.of("Asia/Singapore");
		Clock clockSingapore = Clock.system(zoneSingapore);

		assertEquals(clockSingapore.instant().equals(null), false);
		log.debug("clockSingapore instant : " + clockSingapore.instant());

		ZoneId zoneCalcutta = ZoneId.of("Asia/Calcutta");
		Clock clockCalcutta = clockSingapore.withZone(zoneCalcutta);
		assertEquals(clockCalcutta.instant().equals(null), false);
		log.debug("clockCalcutta instant : " + clockSingapore.instant());
	}

	@Test
	public void givenClock_usingGetZone_retrievesZoneId() {
		
		Clock clockDefaultZone = Clock.systemDefaultZone();
		ZoneId zone = clockDefaultZone.getZone();

		assertEquals(zone.getId().equals(null), false);
		log.debug("Default zone instant : " + clockDefaultZone.instant());
	}  
    
    @Test
    public void givenInstant_thenClockFixed() {
        String expected = "2021-10-25T09:58:30Z";
        Clock clock = Clock.fixed(Instant.parse(expected), ZoneId.of("Asia/Shanghai"));

        assertEquals(expected, clock.instant().toString());
    }
}
