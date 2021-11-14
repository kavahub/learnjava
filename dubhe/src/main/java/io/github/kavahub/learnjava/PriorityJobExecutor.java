package io.github.kavahub.learnjava;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriorityJobExecutor {
    private ExecutorService jobExecutor;
    private ExecutorService mainExecutor;
    private PriorityBlockingQueue<Job> queue;
    private final int QUEUE_SIZE;
    private final boolean isUserJobExecutor;

    private PriorityJobExecutor(ExecutorService jobExecutor, int queueSize, boolean isUserJobExecutor) {
        this.jobExecutor = jobExecutor;
        this.QUEUE_SIZE = queueSize;
        this.isUserJobExecutor = isUserJobExecutor;
    }

    private void onReady() {
        if (QUEUE_SIZE <= 0) {
            throw new IllegalArgumentException("queueSize");
        }
        if (jobExecutor == null) {
            throw new IllegalArgumentException("jobExecutor");
        }

        this.queue = new PriorityBlockingQueue<Job>(QUEUE_SIZE, Comparator.comparing(Job::getPriority));

        mainExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "JobPrioritySchedulerMain");
                thread.setDaemon(true);
                return thread;
            }
        });

        mainExecutor.execute(() -> {
            while (true) {
                try {
                    final Job job = queue.take();

                    if (log.isDebugEnabled()) {
                        log.debug("[{}] Ready to execute", job.toString());
                    }
                    jobExecutor.execute(job);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        if (log.isDebugEnabled()) {
            log.debug("Initialization succeeded");
        }
    }

    private static ExecutorService creatJobExecutorService() {
        return Executors.newFixedThreadPool(1, new ThreadFactory() {
            private int index = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "JobPrioritySchedulerExecutor - " + index++);
                return thread;
            }
        });
    }

    public static PriorityJobExecutor newScheduler() {
        PriorityJobExecutor rslt = new PriorityJobExecutor(creatJobExecutorService(), 10, true);
        rslt.onReady();
        return rslt;
    }

    public static PriorityJobExecutor newScheduler(int queueSize) {
        PriorityJobExecutor rslt = new PriorityJobExecutor(creatJobExecutorService(), queueSize, true);
        rslt.onReady();
        return rslt;
    }

    public static PriorityJobExecutor newScheduler(ExecutorService jobExecutor, int queueSize) {
        PriorityJobExecutor rslt = new PriorityJobExecutor(jobExecutor, queueSize, false);
        rslt.onReady();
        return rslt;
    }

    public PriorityJobExecutor addJob(Job job) {
        if (mainExecutor.isShutdown()) {
            throw new IllegalStateException("Scheduler is closed");
        }
        queue.add(job);
        return this;
    }

    public int getWaitTaskCount() {
        return queue.size();
    }

    public void close() {
        if (log.isDebugEnabled()) {
            log.debug("Ready to close");
        }
        ExecutorServiceHelper.close(mainExecutor);
        if (this.isUserJobExecutor) {
            ExecutorServiceHelper.close(jobExecutor);
        }
    }

    @Getter
    @ToString
    public static abstract class Job implements Runnable {
        private String name;
        private JobPriority priority;

        public Job() {

        }

        public Job(String jobName) {
            this(jobName, JobPriority.MEDIUM);
        }

        public Job(String jobName, JobPriority jobPriority) {
            this.name = jobName;
            this.priority = jobPriority != null ? jobPriority : JobPriority.MEDIUM;
        }
    }

    public enum JobPriority {
        HIGH, MEDIUM, LOW
    }
}
