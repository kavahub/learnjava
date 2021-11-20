package io.github.kavahub.learnjava;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

/**
 * Java 垃圾回收之 Finalizer
 * 
 * <p>
 * 当垃圾回收系统发现一个对象没有任何的引用的时候，也就是不可到达的时候，会把该对象标记成 finalizable , 并且把它放入到一个 Finalize
 * Queue（F-Queue） 的特殊队列里。
 * 
 * <p>
 * 当系统检测到这个 F-Queue 不为空的时候，就会从队列里弹出一个对象出来，调用其 finalize() 方法，把这个对象标记为
 * finalized，这样，下次GC的时候，这些对象就可以被回收掉了。
 * 
 * <p>
 * 做这些事情应该是在一个或者多个线程里去做的，也可能就是GC线程，但是可以肯定的是，这些线程，优先级要比其他线程低一些，所以占用的 CPU
 * 时间是比较少的。在Java 设计上，默认的 finalize() 方法是空的，它是不希望在这里做太多的事情的，期望尽量不在这里做任何事情。
 * 
 * <p>
 * 这里的理解是比较简单的，很多细节被忽略掉了。比如被放入 F-Queue 的，不是对象本身，而是一个 Finalizer
 * 对象，当一些特殊情况下，比如在一个循环里，不停的创建对象，创建完了没有其他任何的引用，这样，这些对象都被标记为可回收的状态，会有很多的 Finalizer
 * 对象存在，而处理 Finalizer 对象的线程比主线程的优先级要低，创建的速度大于了回收的速度，可能会积攒很多 Finalizer
 * 对象在内存里，这个时候是有可能内存溢出的。
 */
public class CrashedFinalizableExample {
    public static void main(String[] args) throws ReflectiveOperationException {
        for (int i = 0;; i++) {
            new CrashedFinalizableExample(); // 创建对象，但没有引用
            if ((i % 1_000_000) == 0) {
                Class<?> finalizerClass = Class.forName("java.lang.ref.Finalizer");
                Field queueStaticField = finalizerClass.getDeclaredField("queue");
                queueStaticField.setAccessible(true);
                @SuppressWarnings("all")
                ReferenceQueue<Object> referenceQueue = (ReferenceQueue) queueStaticField.get(null);

                Field queueLengthField = ReferenceQueue.class.getDeclaredField("queueLength");
                queueLengthField.setAccessible(true);
                long queueLength = (long) queueLengthField.get(referenceQueue);
                System.out.format("There are %d references in the queue%n", queueLength);
            }
        }
    }

    @Override
    protected void finalize() {
        System.out.print("");
    }
}
