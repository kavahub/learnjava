package io.github.kavahub.learnjava.tester;

import static org.junit.Assert.assertEquals;

import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedAfter;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;

import org.junit.Test;

/**
 * 
 * threadweaver 框架测试
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MyCounterThreadWeaverManualTest {
    private MyCounter counter;

	@ThreadedBefore
	public void before() {
		counter = new MyCounter();
	}

	@ThreadedMain
	public void mainThread() {
		counter.increment();
	}

	@ThreadedSecondary
	public void secondThread() {
		counter.increment();
	}

	@ThreadedAfter
	public void after() {
		assertEquals(2, counter.getCount());
	}

	@Test
	public void testCounter() {
		new AnnotatedTestRunner().runTests(this.getClass(), MyCounter.class);
	}

}
