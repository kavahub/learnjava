package io.github.kavahub.learnjava.tester;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

public class MyCounterTempusFugitManualTest {
    @Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

	private static MyCounter counter = new MyCounter();

	@Test
	@Concurrent(count = 3)
	@Repeating(repetition = 10)
	public void runsMultipleTimes() {
		counter.increment();
	}

	@AfterClass
	public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
		assertEquals(30, counter.getCount());
	}
}
