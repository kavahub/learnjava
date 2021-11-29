package io.github.kavahub.learnjava;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 
 * Fork/Join 思想的主旨是将大任务分成若干小任务，之后再并行对这些小任务进行计算，最终汇总这些任务的结果
 * 
 * <p>
 * {@link ForkJoinPool} 是线程池，采取工作窃取算法，以避免工作线程由于拆分了任务之后的join等待过程。这样处于空闲的工作线程将从其他工作线程的队列中主动去窃取任务来执行
 
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ForkJoinTest {
    @Test
    void givenSequentialStreamOfNumbers_whenReducingSumWithIdentityFive_thenResultIsCorrect() {
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        int sum = listOfNumbers.stream().reduce(5, Integer::sum);
        assertThat(sum).isEqualTo(15);
    }

    @Test
    void givenParallelStreamOfNumbers_whenReducingSumWithIdentityFive_thenResultIsNotCorrect() {
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        int sum = listOfNumbers.parallelStream().reduce(5, Integer::sum);
        assertThat(sum).isNotEqualTo(15);
    }

    @Test
    void givenParallelStreamOfNumbers_whenReducingSumWithIdentityZero_thenResultIsCorrect() {
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        int sum = listOfNumbers.parallelStream().reduce(0, Integer::sum) + 5;
        assertThat(sum).isEqualTo(15);
    }

    @Test
    public void givenParallelStreamOfNumbers_whenUsingCustomThreadPool_thenResultIsCorrect()
      throws InterruptedException, ExecutionException {
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        int sum = customThreadPool.submit(
          () -> listOfNumbers.parallelStream().reduce(0, Integer::sum)).get();
        customThreadPool.shutdown();
        assertThat(sum).isEqualTo(10);
    }    
}
