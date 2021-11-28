package io.github.kavahub.learnjava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link Thread} 与 {@link Runnable} 应用比较
 * 
 * <p>
 * {@code Thread#join()} 的使用场景：
 * <p>
 * 在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，主线程将可能早于子线程结束。
 * 如果主线程需要知道子线程的执行结果时，就需要等待子线程执行结束了。主线程可以sleep(xx),但这样的xx时间不好确定，
 * 因为子线程的执行时间不确定
 * <p>
 * {@code Thread#join()} 方法的作用是等待这个线程结束，是主线程等待子线程的终止。也就是说主线程的代码块中，
 * 如果碰到了 {@code Thread#join()} 方法，此时主线程需要等待（阻塞），等待子线程结束了(Waits for this thread to die.),
 * 才能继续执行 {@code Thread#join()} 之后的代码块。
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class RunnableVsThreadManualTest {
	private static ExecutorService executorService;

	@BeforeAll
	public static void setup() {
		executorService = Executors.newCachedThreadPool();
	}

	@Test
	public void givenARunnable_whenRunIt_thenResult() throws Exception {
		Thread thread = new Thread(new SimpleRunnable(
				"SimpleRunnable executed using Thread"));
		thread.start();

		thread.join();
	}

	@Test
	public void givenARunnable_whenSubmitToES_thenResult() throws Exception {
		executorService.submit(new SimpleRunnable(
				"SimpleRunnable executed using ExecutorService")).get();
	}

	@Test
	public void givenARunnableLambda_whenSubmitToES_thenResult()
			throws Exception {

		executorService.submit(() -> log.info("Lambda runnable executed!!!")).get();
	}

	@Test
	public void givenAThread_whenRunIt_thenResult() throws Exception {
		Thread thread = new SimpleThread(
				"SimpleThread executed using Thread");
		thread.start();
		thread.join();
	}

	@Test
	public void givenAThread_whenSubmitToES_thenResult() throws Exception {

		executorService.submit(new SimpleThread(
				"SimpleThread executed using ExecutorService")).get();
	}

	@Test
	public void givenACallable_whenSubmitToES_thenResult() throws Exception {

		Future<Integer> future = executorService.submit(
				new SimpleCallable());
		log.info("Result from callable: {}", future.get());
	}

	@Test
	public void givenACallableAsLambda_whenSubmitToES_thenResult()
			throws Exception {

		Future<Integer> future = executorService.submit(() -> RandomUtils.nextInt(0, 100));

		log.info("Result from callable: {}", future.get());
	}

	@AfterAll
	public static void tearDown() {
		if (executorService != null && !executorService.isShutdown()) {
			executorService.shutdown();
		}
	}

	static class SimpleThread extends Thread {

		private String message;

		SimpleThread(String message) {
			this.message = message;
		}

		@Override
		public void run() {
			log.info(message);
		}
	}

	static class SimpleRunnable implements Runnable {
		private String message;

		SimpleRunnable(String message) {
			this.message = message;
		}

		@Override
		public void run() {
			log.info(message);
		}
	}

	static class SimpleCallable implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			return RandomUtils.nextInt(0, 100);
		}

	}

}
