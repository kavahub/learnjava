package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * {@link ConcurrentSkipListMap}
 * 简单说是TreeMap的并发实现，但是为什么没有称之为ConcurrentTreeMap呢？这和其自身的实现有关。
 * 该类是SkipLists的变种实现，提供了log(n)的时间开销：containsKey、get、put、remove。Insertion,
 * removal, update, and access
 * 等操作都是线程安全的。迭代器是弱一致性的，升序迭代器比降序的快。该map的size方法不是常量时间开销，
 * 需要遍历，所以这个值在并发的时候可能不准。该map也不允许空键或值
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class ConcurrentSkipListMapTest {
    @Test
    public void givenThreadsProducingEvents_whenGetForEventsFromLastMinute_thenReturnThoseEventsInTheLockFreeWay()
            throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        EventWindowSort eventWindowSort = new EventWindowSort();
        int numberOfThreads = 2;
        // when
        Runnable producer = () -> IntStream.rangeClosed(0, 100).forEach(index -> eventWindowSort
                .acceptEvent(new Event(ZonedDateTime.now().minusSeconds(index), UUID.randomUUID().toString())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, String> eventsFromLastMinute = eventWindowSort.getEventsFromLastMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute.entrySet().stream()
                .filter(e -> e.getKey().isBefore(ZonedDateTime.now().minusMinutes(1))).count();
        assertEquals(eventsOlderThanOneMinute, 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute.entrySet().stream()
                .filter(e -> e.getKey().isAfter(ZonedDateTime.now().minusMinutes(1))).count();

        // then
        assertTrue(eventYoungerThanOneMinute > 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    @Test
    public void givenThreadsProducingEvents_whenGetForEventsOlderThanOneMinute_thenReturnThoseEventsInTheLockFreeWay()
            throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        EventWindowSort eventWindowSort = new EventWindowSort();
        int numberOfThreads = 2;
        // when
        Runnable producer = () -> IntStream.rangeClosed(0, 100).forEach(index -> eventWindowSort
                .acceptEvent(new Event(ZonedDateTime.now().minusSeconds(index), UUID.randomUUID().toString())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, String> eventsFromLastMinute = eventWindowSort
                .getEventsOlderThatOneMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute.entrySet().stream()
                .filter(e -> e.getKey().isBefore(ZonedDateTime.now().minusMinutes(1))).count();
        assertTrue(eventsOlderThanOneMinute > 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute.entrySet().stream()
                .filter(e -> e.getKey().isAfter(ZonedDateTime.now().minusMinutes(1))).count();

        // then
        assertEquals(eventYoungerThanOneMinute, 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    static class EventWindowSort {
        private final ConcurrentSkipListMap<ZonedDateTime, String> events = new ConcurrentSkipListMap<>(
                Comparator.comparingLong(value -> value.toInstant().toEpochMilli()));

        void acceptEvent(Event event) {
            events.put(event.getEventTime(), event.getContent());
        }

        ConcurrentNavigableMap<ZonedDateTime, String> getEventsFromLastMinute() {
            // 返回一个包含了不小于给定 fromKey 的 key 的子 map
            return events.tailMap(ZonedDateTime.now().minusMinutes(1));
        }

        ConcurrentNavigableMap<ZonedDateTime, String> getEventsOlderThatOneMinute() {
            // 返回一个包含了小于给定 toKey 的 key 的子 map
            return events.headMap(ZonedDateTime.now().minusMinutes(1));
        }

    }

    static class Event {
        private final ZonedDateTime eventTime;
        private final String content;

        Event(ZonedDateTime eventTime, String content) {
            this.eventTime = eventTime;
            this.content = content;
        }

        ZonedDateTime getEventTime() {
            return eventTime;
        }

        String getContent() {
            return content;
        }
    }
}
