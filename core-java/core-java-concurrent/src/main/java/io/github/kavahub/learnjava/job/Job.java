package io.github.kavahub.learnjava.job;

/**
 * 
 * 任务定义
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Job implements Runnable {
    private String jobName;
    private JobPriority jobPriority;
    
    public Job(String jobName, JobPriority jobPriority) {
        this.jobName = jobName;
        this.jobPriority = jobPriority != null ? jobPriority : JobPriority.MEDIUM;
    }

    public JobPriority getJobPriority() {
        return jobPriority;
    }

    @Override
    public void run() {
        try {
            System.out.println("Job:" + jobName + 
              " Priority:" + jobPriority);
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
