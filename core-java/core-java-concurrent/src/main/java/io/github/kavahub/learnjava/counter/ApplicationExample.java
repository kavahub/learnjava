package io.github.kavahub.learnjava.counter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApplicationExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        new Thread(() -> {
            System.out.println(MathUtils.factorial(10));
        }).start();
        new Thread(() -> {
            System.out.println(MathUtils.factorial(5));
        }).start();
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
       
        MessageService messageService = new MessageService("Welcome to Learnjava!");
        Future<String> future1 = (Future<String>) executorService.submit(new MessageServiceCallable(messageService));
        Future<String> future2 = (Future<String>) executorService.submit(new MessageServiceCallable(messageService));
        System.out.println(future1.get());
        System.out.println(future2.get());
        
        Counter counter = new Counter();
        Future<Integer> future3 = (Future<Integer>) executorService.submit(new CounterCallable(counter));
        Future<Integer> future4 = (Future<Integer>) executorService.submit(new CounterCallable(counter));
        System.out.println(future3.get());
        System.out.println(future4.get());
        
        ObjectLockCounter objectLockCounter = new ObjectLockCounter();
        Future<Integer> future5 = (Future<Integer>) executorService.submit(new ObjectLockCounterCallable(objectLockCounter));
        Future<Integer> future6 = (Future<Integer>) executorService.submit(new ObjectLockCounterCallable(objectLockCounter));
        System.out.println(future5.get());
        System.out.println(future6.get());
        
        ReentrantLockCounter reentrantLockCounter = new ReentrantLockCounter();
        Future<Integer> future7 = (Future<Integer>) executorService.submit(new ReentrantLockCounterCallable(reentrantLockCounter));
        Future<Integer> future8 = (Future<Integer>) executorService.submit(new ReentrantLockCounterCallable(reentrantLockCounter));
        System.out.println(future7.get());
        System.out.println(future8.get());
        
        ReentrantReadWriteLockCounter reentrantReadWriteLockCounter = new ReentrantReadWriteLockCounter();
        Future<Integer> future9 = (Future<Integer>) executorService.submit(new ReentranReadWriteLockCounterCallable(reentrantReadWriteLockCounter));
        Future<Integer> future10 = (Future<Integer>) executorService.submit(new ReentranReadWriteLockCounterCallable(reentrantReadWriteLockCounter));
        System.out.println(future9.get());
        System.out.println(future10.get());
        
        AtomicCounter atomicCounter = new AtomicCounter();
        Future<Integer> future11 = (Future<Integer>) executorService.submit(new AtomicCounterCallable(atomicCounter));
        Future<Integer> future12 = (Future<Integer>) executorService.submit(new AtomicCounterCallable(atomicCounter));
        System.out.println(future11.get());
        System.out.println(future12.get());
        
        
        SemaphoreCounter semaphoreCounter = new SemaphoreCounter();
        Future<Integer> future13 = (Future<Integer>) executorService.submit(new SemaphoreCounterCallable(semaphoreCounter));
        Future<Integer> future14 = (Future<Integer>) executorService.submit(new SemaphoreCounterCallable(semaphoreCounter));
        System.out.println(future13.get());
        System.out.println(future14.get());

        NonBlockCounter nonBlockCounter = new NonBlockCounter();
        Future<Integer> future15 = (Future<Integer>) executorService.submit(new NonBlockCounterCallable(nonBlockCounter));
        Future<Integer> future16 = (Future<Integer>) executorService.submit(new NonBlockCounterCallable(nonBlockCounter));
        System.out.println(future15.get());
        System.out.println(future16.get());

        Map<String,String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("1", "one");
        concurrentMap.put("2", "two");
        concurrentMap.put("3", "three");

        // 必须关闭，否则主线程（main）无法结束
        executorService.shutdown();
    }    
}
