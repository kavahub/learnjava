package io.github.kavahub.learnjava;

import lombok.extern.slf4j.Slf4j;

/**
 * 秒表，计算开始到结束直接的毫秒数
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class StopWatch {
    /**
     * 
     * 静态方法实现
     *
     * @author PinWei Wan
     * @since 1.0.1
     */
    public static class StaticClazz {
        static ThreadLocal<Long> t = new ThreadLocal<Long>();
    
        public static void start() {
            t.set(System.currentTimeMillis());
        }
    
        public static void end() {
            final long elapseOfTime = System.currentTimeMillis() - t.get();
            log.info("{} elapse of time: {}", Thread.currentThread().getStackTrace()[2] , elapseOfTime);
    
            t.remove();          
        }
    
    }

    /**
     * 
     * 类实现
     *
     * @author PinWei Wan
     * @since 1.0.1
     */
    public static class Clazz {
        private final String methodName;
        private long start;
    
        public Clazz(String methodName) {
            this.methodName = methodName;
        }

        public  void start() {
            start = System.currentTimeMillis();
        }
    
        public void end() {
            final long elapseOfTime = System.currentTimeMillis() - start;
            log.info("{} elapse of time: {}", methodName , elapseOfTime);
       
        }
    
    }

}
