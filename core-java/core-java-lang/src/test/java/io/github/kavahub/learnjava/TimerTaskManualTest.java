package io.github.kavahub.learnjava;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link TimerTask} 示例
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class TimerTaskManualTest {
    @Test
    public void givenUsingTimer_whenSchedulingTimerTaskOnce_thenCorrect() throws InterruptedException {
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                log.debug("Time when was task performed" + new Date());
                log.debug("Thread's name: " + Thread.currentThread().getName());
            }
        };
        final Timer timer = new Timer("Thread's name");
        log.debug("Current time:" + new Date());
        log.debug("Thread's name: " + Thread.currentThread().getName());
        final long delay = 2L * 1000L;
        timer.schedule(timerTask, delay);
        Thread.sleep(delay);
    }

    @Test
    public void givenUsingTimer_whenSchedulingRepeatedTask_thenCorrect() throws InterruptedException {
        final TimerTask repeatedTask = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                log.debug("Time when task was performed: " + new Date());
                log.debug("Thread's name: " + Thread.currentThread().getName());
                if (count >= 5) {
                    cancel();
                }
            }
        };
        final Timer timer = new Timer("Timer thread");
        log.debug("Current time: " + new Date());
        log.debug("Thread's name: " + Thread.currentThread().getName());
        final long delay = 2L * 1000L;
        final long period = 1L * 1000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        Thread.sleep(delay + period * 5L);
    }

    @Test
    public void givenUsingTimer_whenSchedulingRepeatedCustomTimerTask_thenCorrect() throws InterruptedException {
        class MyTask extends TimerTask {
            long timesToRun = 0;
            long timesRunned = 0;

            public void setTimesToRun(final int timesToRun) {
                this.timesToRun = timesToRun;
            }

            @Override
            public void run() {
                timesRunned++;
                log.debug("Task performed on: " + new Date());
                log.debug("Thread's name: " + Thread.currentThread().getName());
                if (timesRunned >= timesToRun) {
                    cancel();
                }
            }
        }
        final MyTask repeatedTask = new MyTask();
        repeatedTask.setTimesToRun(5);
        final long delay = 2L * 1000L;
        final long period = 1000L;
        final Timer timer = new Timer("Timer");
        log.debug("Current time: " + new Date());
        log.debug("Thread's name: " + Thread.currentThread().getName());
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        Thread.sleep(delay + period * repeatedTask.timesToRun);
    }
    
}
