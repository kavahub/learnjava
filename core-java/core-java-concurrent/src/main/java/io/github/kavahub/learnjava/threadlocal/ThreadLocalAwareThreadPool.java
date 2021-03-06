package io.github.kavahub.learnjava.threadlocal;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * {@link ThreadLocal} 适配线程池
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ThreadLocalAwareThreadPool extends ThreadPoolExecutor {

    public ThreadLocalAwareThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory,
      RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // Call remove on each ThreadLocal
    }
    
}
