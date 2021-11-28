package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * ABA 问题
 * 
 * <p>
 * 描述ABA: 假设两个线程T1和T2访问同一个变量V，当T1访问变量V时，读取到V的值为A；此时线程T1被抢占了，T2开始执行，
 * T2先将变量V的值从A变成了B，然后又将变量V从B变回了A；此时T1又抢占了主动权，继续执行，它发现变量V的值还是A，以为没有发生变化，
 * 所以就继续执行了。这个过程中，变量V从A变为B，再由B变为A就被形象地称为ABA问题了
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ABAProblemTest {
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
    }

    @Test
    public void zeroBalanceInitializationTest() {
        assertEquals(0, account.getBalance());
        assertEquals(0, account.getTransactionCount());
        assertEquals(0, account.getCurrentThreadCASFailureCount());
    }

    @Test
    public void depositTest() {
        final int moneyToDeposit = 50;

        assertTrue(account.deposit(moneyToDeposit));

        assertEquals(moneyToDeposit, account.getBalance());
    }

    @Test
    public void withdrawTest() throws InterruptedException {
        final int defaultBalance = 50;
        final int moneyToWithdraw = 20;

        account.deposit(defaultBalance);

        assertTrue(account.withdraw(moneyToWithdraw));

        assertEquals(defaultBalance - moneyToWithdraw, account.getBalance());
    }

    @Test
    public void abaProblemTest() throws InterruptedException {
        final int defaultBalance = 50;

        final int amountToWithdrawByThread1 = 20;
        final int amountToWithdrawByThread2 = 10;
        final int amountToDepositByThread2 = 10;

        assertEquals(0, account.getTransactionCount());
        assertEquals(0, account.getCurrentThreadCASFailureCount());
        account.deposit(defaultBalance);
        assertEquals(1, account.getTransactionCount());

        Thread thread1 = new Thread(() -> {

            // this will take longer due to the name of the thread
            assertTrue(account.withdraw(amountToWithdrawByThread1));

            // thread 1 fails to capture ABA problem
            assertNotEquals(1, account.getCurrentThreadCASFailureCount());

        }, "thread1");

        Thread thread2 = new Thread(() -> {

            assertTrue(account.deposit(amountToDepositByThread2));
            assertEquals(defaultBalance + amountToDepositByThread2, account.getBalance());

            // this will be fast due to the name of the thread
            assertTrue(account.withdraw(amountToWithdrawByThread2));

            // thread 1 didn't finish yet, so the original value will be in place for it
            assertEquals(defaultBalance, account.getBalance());

            assertEquals(0, account.getCurrentThreadCASFailureCount());
        }, "thread2");

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // compareAndSet operation succeeds for thread 1
        assertEquals(defaultBalance - amountToWithdrawByThread1, account.getBalance());

        //but there are other transactions
        assertNotEquals(2, account.getTransactionCount());

        // thread 2 did two modifications as well
        assertEquals(4, account.getTransactionCount());
    }
    
    public class Account {

        private AtomicInteger balance;
        private AtomicInteger transactionCount;
        private ThreadLocal<Integer> currentThreadCASFailureCount;
    
        public Account() {
            this.balance = new AtomicInteger(0);
            this.transactionCount = new AtomicInteger(0);
            this.currentThreadCASFailureCount = new ThreadLocal<>();
            this.currentThreadCASFailureCount.set(0);
        }
    
        public int getBalance() {
            return balance.get();
        }
    
        public int getTransactionCount() {
            return transactionCount.get();
        }
    
        public int getCurrentThreadCASFailureCount() {
            return currentThreadCASFailureCount.get();
        }
    
        public boolean withdraw(int amount) {
            int current = getBalance();
            maybeWait();
            boolean result = balance.compareAndSet(current, current - amount);
            if (result) {
                transactionCount.incrementAndGet();
            } else {
                int currentCASFailureCount = currentThreadCASFailureCount.get();
                currentThreadCASFailureCount.set(currentCASFailureCount + 1);
            }
            return result;
        }
    
        private void maybeWait() {
            if ("thread1".equals(Thread.currentThread().getName())) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    
        public boolean deposit(int amount) {
            int current = balance.get();
            boolean result = balance.compareAndSet(current, current + amount);
            if (result) {
                transactionCount.incrementAndGet();
            } else {
                int currentCASFailureCount = currentThreadCASFailureCount.get();
                currentThreadCASFailureCount.set(currentCASFailureCount + 1);
            }
            return result;
        }
    
    }
    
}
