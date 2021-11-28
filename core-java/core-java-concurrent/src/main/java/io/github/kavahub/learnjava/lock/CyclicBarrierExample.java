package io.github.kavahub.learnjava.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * {@link CyclicBarrier} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CyclicBarrierExample {
    private CyclicBarrier cyclicBarrier;
    private List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());
    private Random random = new Random();
    private int NUM_PARTIAL_RESULTS;
    private int NUM_WORKERS;

    void runSimulation(int numWorkers, int numberOfPartialResults) {
        NUM_PARTIAL_RESULTS = numberOfPartialResults;
        NUM_WORKERS = numWorkers;

        // 当满足条件后，运行任务
        cyclicBarrier = new CyclicBarrier(NUM_WORKERS, new AggregatorThread());
        System.out.println("Spawning " + NUM_WORKERS + " worker threads to compute " + NUM_PARTIAL_RESULTS + " partial results each");
        for (int i = 0; i < NUM_WORKERS; i++) {
            Thread worker = new Thread(new NumberCruncherThread());
            worker.setName("Thread " + i);
            worker.start();
        }
    }

    /**
     * 生成数字任务
     */
    class NumberCruncherThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            List<Integer> partialResult = new ArrayList<>();
            for (int i = 0; i < NUM_PARTIAL_RESULTS; i++) {
                Integer num = random.nextInt(10);
                System.out.println(thisThreadName + ": Crunching some numbers! Final result - " + num);
                partialResult.add(num);
            }
            partialResults.add(partialResult);
            try {
                System.out.println(thisThreadName + " waiting for others to reach barrier.");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合计任务
     */
    class AggregatorThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();
            System.out.println(thisThreadName + ": Computing final sum of " + NUM_WORKERS + " workers, having " + NUM_PARTIAL_RESULTS + " results each.");
            int sum = 0;
            for (List<Integer> threadResult : partialResults) {
                System.out.print("Adding ");
                for (Integer partialResult : threadResult) {
                    System.out.print(partialResult + " ");
                    sum += partialResult;
                }
                System.out.println();
            }
            System.out.println(Thread.currentThread().getName() + ": Final result = " + sum);
        }

    }

    public static void main(String[] args) {
        CyclicBarrierExample play = new CyclicBarrierExample();
        play.runSimulation(5, 3);
    }
}
