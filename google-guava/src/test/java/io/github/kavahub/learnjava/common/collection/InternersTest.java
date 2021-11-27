package io.github.kavahub.learnjava.common.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.testing.GcFinalization;
import com.google.common.testing.NullPointerTester;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Interners} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class InternersTest {

  @Test
  public void testStrong_simplistic() {
    String canonical = "a";
    String not = new String("a");

    Interner<String> pool = Interners.newStrongInterner();
    assertSame(canonical, pool.intern(canonical));
    assertSame(canonical, pool.intern(not));
  }

  @Test
  public void testStrong_null() {
    Interner<String> pool = Interners.newStrongInterner();
    assertThrows(NullPointerException.class, () -> pool.intern(null));
  }

  @Test
  public void testWeak_simplistic() {
    String canonical = "a";
    String not = new String("a");

    Interner<String> pool = Interners.newWeakInterner();
    assertSame(canonical, pool.intern(canonical));
    assertSame(canonical, pool.intern(not));
  }

  @Test
  public void testWeak_null() {
    Interner<String> pool = Interners.newWeakInterner();
    assertThrows(NullPointerException.class, () -> pool.intern(null));
  }

  @Test
  @SuppressWarnings("deprecation")
  public void testWeak_afterGC() throws InterruptedException {
    Integer canonical = new Integer(5);
    Integer not = new Integer(5);

    Interner<Integer> pool = Interners.newWeakInterner();
    assertSame(canonical, pool.intern(canonical));

    WeakReference<Integer> signal = new WeakReference<>(canonical);
    canonical = null; // Hint to the JIT that canonical is unreachable

    GcFinalization.awaitClear(signal);
    assertSame(not, pool.intern(not));
  }

  @Test
  public void testAsFunction_simplistic() {
    String canonical = "a";
    String not = new String("a");

    Function<String, String> internerFunction = Interners.asFunction(Interners.<String>newStrongInterner());

    assertSame(canonical, internerFunction.apply(canonical));
    assertSame(canonical, internerFunction.apply(not));
  }

  @Test
  public void testNullPointerExceptions() {
    new NullPointerTester().testAllPublicStaticMethods(Interners.class);
  }

  @Test
  public void testConcurrencyLevel_Zero() {
    Interners.InternerBuilder builder = Interners.newBuilder();
    assertThrows(IllegalArgumentException.class, () -> builder.concurrencyLevel(0));
  }

  @Test
  public void testConcurrencyLevel_Negative() {
    Interners.InternerBuilder builder = Interners.newBuilder();
    assertThrows(IllegalArgumentException.class, () -> builder.concurrencyLevel(-42));
  }

  @Test
  public void givenNewString_whenIntern_thenTheSame() {

    Interner<String> interner = Interners.newBuilder().build();

    assertNotNull(interner);

    final String one = new String("abc");
    final String two = new String("abc");
    assertThat(one).isEqualTo(two);
    assertThat(one).isNotSameAs(two);
    assertFalse(one == two);

    assertTrue(interner.intern(one) == interner.intern(two));
  }

  /**
   * 在实际业务中，需要避免同一个任务同时执行多次， 可以使用 {@link Interners} 创建一个锁池， 让任务按序执行。
   * 
   * @throws InterruptedException
   */
  @Test
  public void givenStringLockPool_whenMultiThreadSubmit_thenSynchronous() throws InterruptedException {
    final Interner<String> pool = Interners
        // 线程安全的，弱引用
        .newWeakInterner();

    // 订单号
    final String orderId = "123456";

    // 模拟多个个线程同时处理一个订单
    final int threads = 4;
    ExecutorService service = Executors.newFixedThreadPool(threads);
    Stream.generate(() -> new Runnable() {
      public void run() {
        // 使用创建String实例的方式，LOCK是不同的对象(相同的值)，所以无法同步
        // final String LOCK = new String("Order_" + orderId);
        final String LOCK = pool.intern("Order_" + orderId);
        synchronized (LOCK) {
          long point = System.currentTimeMillis();
          System.out.println("处理订单开始: " + point);

          try {
            TimeUnit.MILLISECONDS.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          // do something else

          System.out.println("处理订单结束：" + point);
        }
      }
    }).limit(threads).forEach(task -> service.submit(task));

    service.shutdown();
    service.awaitTermination(60, TimeUnit.SECONDS);
  }
}
