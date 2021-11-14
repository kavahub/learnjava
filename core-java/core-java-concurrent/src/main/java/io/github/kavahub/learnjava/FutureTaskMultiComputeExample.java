package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 
 * 
 * FutureTask可用于异步获取执行结果或取消执行任务的场景
 * 
 * @see <a href="https://blog.csdn.net/chenliguan/article/details/54345993">Java多线程之FutureTask的用法及解析</a>
 */
public class FutureTaskMultiComputeExample {
    public static void main(String[] args) {
        FutureTaskMultiComputeExample example =new FutureTaskMultiComputeExample();
        // 创建任务集合
        List<FutureTask<Integer>> tasks = new ArrayList<FutureTask<Integer>>();
        
        // 创建线程池
        ExecutorService exec = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            FutureTask<Integer> task = new FutureTask<Integer>(example.new ComputeTask(i));
            tasks.add(task);
            exec.submit(task);
        }
        
        System.out.println("任务提交完成");
 
        // 开始统计各计算线程计算结果
        Integer totalResult = 0;
        for (FutureTask<Integer> ft : tasks) {
            try {
                //FutureTask的get方法会自动阻塞,直到获取计算结果为止
                totalResult = totalResult + ft.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
 
        // 关闭线程池
        exec.shutdown();
        System.out.println("多任务计算后的总结果是:" + totalResult);
 
    }

    private class ComputeTask implements Callable<Integer> {
 
        private Integer result = 0;
        
        public ComputeTask(Integer iniResult){
            result = iniResult;
        }
        
        
        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() +" 开始...");
            for (int i = 0; i < 100; i++) {
                result =+ i;
            }
            // 模拟长时间业务
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() +" 完成");
            return result;
        }
    }
}
