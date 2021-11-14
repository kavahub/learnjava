package io.github.kavahub.learnjava;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用ReentrantLock和Condition实现阻塞站
 */
@Slf4j
public class ReentrantLockWithConditionExample {
    private Stack<String> stack = new Stack<>();
    private static final int CAPACITY = 5;

    private ReentrantLock lock = new ReentrantLock();
    private Condition stackEmptyCondition = lock.newCondition();
    private Condition stackFullCondition = lock.newCondition();

    private void pushToStack(String item) throws InterruptedException {
        try {
            lock.lock();
            if (stack.size() == CAPACITY) {
                log.info(Thread.currentThread().getName() + " wait on stack full");
                stackFullCondition.await();
            }
            log.info("Pushing the item " + item);
            stack.push(item);
            stackEmptyCondition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    private String popFromStack() throws InterruptedException {
        try {
            lock.lock();
            if (stack.size() == 0) {
                log.info(Thread.currentThread().getName() + " wait on stack empty");
                stackEmptyCondition.await();
            }
            return stack.pop();
        } finally {
            stackFullCondition.signalAll();
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final int threadCount = 4;
        ReentrantLockWithConditionExample object = new ReentrantLockWithConditionExample();
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        Runnable push = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    object.pushToStack("Item " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        Runnable pop = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    log.info("Item popped " + object.popFromStack());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };


        service.execute(push);
        service.execute(pop);
        service.execute(pop);
        service.execute(push);

        service.shutdown();
    }
}
