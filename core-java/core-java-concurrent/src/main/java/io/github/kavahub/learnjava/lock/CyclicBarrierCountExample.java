package io.github.kavahub.learnjava.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link CyclicBarrier} 示例
 *
 * <p>
 * {@code CyclicBarrier#isBroken()} 获取是否破损标志位 broken 的值，此值有以下几种情况:
 * <ul>
 * <li> {@code CyclicBarrier} 初始化时，{@code broken=false}，表示屏障未破损</li>
 * <li> 如果正在等待的线程被中断，则 {@code broken=true}，表示屏障破损</li>
 * <li> 如果正在等待的线程超时，则 {@code broken=true}，表示屏障破损</li>
 * <li> 如果有线程调用 {@code CyclicBarrier#reset()} 方法，则 {@code broken=false}，表示屏障回到未破损状态</li>
 * </ul>
 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class CyclicBarrierCountExample {
    private int count;

    public CyclicBarrierCountExample(int count) {
        this.count = count;
    }

    public boolean callTwiceInSameThread() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        Thread t = new Thread(() -> {
            try {
                cyclicBarrier.await();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                cyclicBarrier.await();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        t.start();
        t2.start();

        log.info("Parties: {}",cyclicBarrier.getParties());
        log.info("NumberWaitin: {}",cyclicBarrier.getNumberWaiting());
        
        return cyclicBarrier.isBroken();
    }

    public static void main(String[] args) {
        CyclicBarrierCountExample ex = new CyclicBarrierCountExample(4);
        System.out.println("Count : " + ex.callTwiceInSameThread());
    }
}
