package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.PriorityJobExecutor.Job;
import io.github.kavahub.learnjava.PriorityJobExecutor.JobPriority;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriorityJobExecutorManualTest {
    @Test
    public void givenManyOfJobs_thenCompleted() throws InterruptedException {
        PriorityJobExecutor scheduler = PriorityJobExecutor.newScheduler(1);

        final int JOB_NUMS = 100;
        for (int i = 0; i < JOB_NUMS; i++) {
            scheduler.addJob(new Job("job - " + i) {

                @Override
                public void run() {
                    log.info("Start: {}", getName());
                }
            });
        }

        while (scheduler.getWaitTaskCount() > 0){
            log.info("scheduler.getWaitTaskCount(): {}", scheduler.getWaitTaskCount());
        }

        scheduler.close();
    }

    @Test
    public void givenJob_whenShutdown_thenClosed() throws InterruptedException {
        PriorityJobExecutor scheduler = PriorityJobExecutor.newScheduler();

        Job job = new Job("job1", JobPriority.LOW) {

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    final long worktime = 2 * 1000;
                    final long now = System.currentTimeMillis();
                    while (System.currentTimeMillis() <= now + worktime) {
                        // System.out.println(System.currentTimeMillis());
                    }
                }
                log.info("run complete");
            }
        };

        
        scheduler.addJob(job);

        Thread.sleep(100);

        scheduler.close();
    }

    @Test
    public void whenMultiplePriorityJobsQueued_thenHighestPriorityJobIsPicked() {
        Job job1 = new MyJob("Job1", JobPriority.LOW);
        Job job2 = new MyJob("Job2", JobPriority.MEDIUM);
        Job job3 = new MyJob("Job3", JobPriority.HIGH);
        Job job4 = new MyJob("Job4", JobPriority.MEDIUM);
        Job job5 = new MyJob("Job5", JobPriority.LOW);
        Job job6 = new MyJob("Job6", JobPriority.HIGH);

        PriorityJobExecutor scheduler = PriorityJobExecutor.newScheduler();

        scheduler.addJob(job1);
        scheduler.addJob(job2);
        scheduler.addJob(job3);
        scheduler.addJob(job4);
        scheduler.addJob(job5);
        scheduler.addJob(job6);

        // ensure no tasks is pending before closing the scheduler
        while (scheduler.getWaitTaskCount() > 0)
            ;

        // delay to avoid job sleep (added for demo) being interrupted
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        scheduler.close();
    }

    public class MyJob extends Job {

        public MyJob(String jobName, JobPriority jobPriority) {
            super(jobName, jobPriority);
        }

        @Override
        public void run() {
            System.out.println("Job:" + this.getName() + " Priority:" + this.getPriority());
            final long worktime = 1 * 1000;
            final long now = System.currentTimeMillis();
            while (System.currentTimeMillis() <= now + worktime) {
                // System.out.println(System.currentTimeMillis());
            }
        }

    }
}
