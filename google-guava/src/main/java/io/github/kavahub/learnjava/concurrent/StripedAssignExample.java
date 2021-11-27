package io.github.kavahub.learnjava.concurrent;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.util.concurrent.Striped;

/**
 * 
 * {@link Striped} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class StripedAssignExample {
    private static void test(int strips, int tasks) {
        Striped<Lock> stripedLocks = Striped.lock(strips);
        Set<Lock> used = IntStream.rangeClosed(1, tasks).boxed().map(v -> stripedLocks.get(v + ""))
                .collect(Collectors.toSet());
        System.out.println("total locks: " + strips + ", tasks: " + tasks + ", used locks: " + used.size());
    }

    private static void test2(int strips, int tasks) {
        Striped<Lock> stripedLocks = Striped.lock(strips);
        Set<Lock> used = IntStream.rangeClosed(1, tasks).boxed().map(v -> {
            // System.out.println(stripedLocks.size());
            int bucket = v % stripedLocks.size();
            return stripedLocks.get(bucket);
        })
                .collect(Collectors.toSet());
        System.out.println("total locks: " + strips + ", tasks: " + tasks + ", used locks: " + used.size());
    }

    public static void main(String[] args) {
        System.out.println("******** test ********");
        test(20, 20);
        test(20, 40);
        test(20, 60);
        test(20, 80);

        System.out.println("******** test2 ********");
        test2(20, 20);
        test2(20, 40);
        test2(20, 60);
        test2(20, 80);
    }
}
