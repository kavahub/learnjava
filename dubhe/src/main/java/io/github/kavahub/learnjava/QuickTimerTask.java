package io.github.kavahub.learnjava;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class QuickTimerTask implements Runnable {
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "Quick Timer Task");
            thread.setDaemon(true);
            return thread;
        }
    });

    public QuickTimerTask() {
        final long delay = this.getDelay();
        final long period = this.getPeriod();
        final TimeUnit unit = this.getTimeunit();

        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(this, delay, period, unit);
        if (log.isDebugEnabled()) {
            log.debug("Initialization succeeded");
        }
    }

    public static void shutdown() {
        ExecutorServiceHelper.close(SCHEDULED_EXECUTOR_SERVICE);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long delay, long period) {
        return scheduleAtFixedRate(command, delay, period, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long delay, long period, TimeUnit unit) {
        return SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(command, delay, period, unit);
    }


    /**
     * 获取定时任务的延迟启动时间, 默认是0
     */
    protected long getDelay() {
        return 0;
    }

    /**
     * 时间单位，默认是毫秒
     * @return
     */
    protected TimeUnit getTimeunit() {
        return TimeUnit.MILLISECONDS;
    }

    /**
     * 获取定时任务的执行频率
     *
     * @return
     */
    protected abstract long getPeriod();
    
}
