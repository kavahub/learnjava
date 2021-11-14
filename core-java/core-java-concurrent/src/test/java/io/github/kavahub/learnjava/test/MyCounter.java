package io.github.kavahub.learnjava.test;

public class MyCounter {
    private int count;

    public void increment() {
        int temp = count;
        count = temp + 1;
    }

    /**
     * 也有同步的问题
     * @throws InterruptedException
     */
    public synchronized void incrementWithWait() throws InterruptedException {
        int temp = count;
        // wait释放了锁（使得其他线程可以使用同步控制块或者方法锁）, 相当于同步方法失效了
        wait(100);
        count = temp + 1;
        System.out.println("added:" + count);
    }

    public int getCount() {
        return count;
    }

}
