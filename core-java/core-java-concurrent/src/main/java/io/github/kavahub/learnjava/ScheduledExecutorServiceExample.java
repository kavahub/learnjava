package io.github.kavahub.learnjava;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * {@link ScheduledExecutorService} 是基于线程池设计的定时任务类,每个调度任务都会分配到线程池中的一个线程去执行,
 * 也就是说,任务是并发执行,互不影响。
 * 
 * <p>
 * 需要注意,只有当调度任务来的时候,ScheduledExecutorService才会真正启动一个线程,其余时间ScheduledExecutorService都是出于轮询任务的状态。
 */
public class ScheduledExecutorServiceExample {
    private void execute() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		getTasksToRun().accept(executorService);
		executorService.shutdown();
	}

	private void executeWithMultiThread() {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		getTasksToRun().accept(executorService);
		executorService.shutdown();
	}

	private Consumer<ScheduledExecutorService> getTasksToRun() {
		return (executorService -> {
			executorService.schedule(() -> {
				// Task
                System.out.println("schedule");
			}, 1, TimeUnit.SECONDS);

			executorService.scheduleAtFixedRate(() -> {
				// Task
                System.out.println("scheduleAtFixedRate");
			}, 1, 10, TimeUnit.SECONDS);

			executorService.scheduleWithFixedDelay(() -> {
				// Task
                System.out.println("scheduleWithFixedDelay");
			}, 1, 10, TimeUnit.SECONDS);

			executorService.schedule(() -> {
				// Task
                System.out.println("schedule callable");
				return "Hellow world";
			}, 1, TimeUnit.SECONDS);
		});
	}

	public static void main(String... args) {
		ScheduledExecutorServiceExample demo = new ScheduledExecutorServiceExample();
		demo.execute();
		demo.executeWithMultiThread();
	}    
}
