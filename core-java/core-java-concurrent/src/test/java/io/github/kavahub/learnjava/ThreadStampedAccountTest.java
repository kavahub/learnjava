package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.jupiter.api.Test;

public class ThreadStampedAccountTest {
    @Test
    public void givenMultiThread_whenStampedAccount_thenSetBalance() throws InterruptedException {
        StampedAccount account = new StampedAccount();
        Thread t = new Thread(() -> {
            while (!account.withdrawal(100))
                Thread.yield();
        });
        t.start();
        assertTrue(account.deposit(100));
        t.join(1_000);
        assertFalse(t.isAlive());
        assertSame(0, account.getBalance());
    }

    /**
     * AtomicStampedReference[əˈtɒmɪk]在构建的时候需要一个类似于版本号的int类型变量stamped，
     * 每一次针对共享数据的变化都会导致该 stamped 的变化（stamped 需要应用程序自身去负责，
     * AtomicStampedReference并不提供，一般使用时间戳作为版本号），因此就可以避免ABA问题的出现，
     * AtomicStampedReference的使用也是极其简单的，创建时我们不仅需要指定初始值，还需要设定stamped的初始值，
     * 在AtomicStampedReference的内部会将这两个变量封装成Pair对象
     */
    public static class StampedAccount {

        private AtomicInteger stamp = new AtomicInteger(0);
        private AtomicStampedReference<Integer> account = new AtomicStampedReference<>(0, 0);
    
        public int getBalance() {
            return account.getReference();
        }
    
        public int getStamp() {
            return account.getStamp();
        }
    
        public boolean deposit(int funds) {
            int[] stamps = new int[1];
            int current = this.account.get(stamps);
            int newStamp = this.stamp.incrementAndGet();
            return this.account.compareAndSet(current, current + funds, stamps[0], newStamp);
        }
    
        public boolean withdrawal(int funds) {
            int[] stamps = new int[1];
            int current = this.account.get(stamps);
            int newStamp = this.stamp.incrementAndGet();
            return this.account.compareAndSet(current, current - funds, stamps[0], newStamp);
        }
    }
    
}
