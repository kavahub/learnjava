package io.github.kavahub.learnjava.common.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

import org.junit.jupiter.api.Test;

/**
 * {@link LoadingCache} 一种非常优秀本地缓存解决方案
 * 
 * <p>
 * 缓存回收策略：基于容量回收，定时回收，基于引用回收
 * <ul>
 * <li> {@link CacheBuilder#maximumSize(long)} ：当缓存中的元素数量超过指定值时</li>
 * <li> {@link CacheBuilder#maximumWeight(long)} ：当缓存中的元素重量超过指定值时。使用 <code>CacheBuilder.weigher(Weigher)</code> 计算缓存重量</li>
 * <li> {@link CacheBuilder#expireAfterAccess(long, TimeUnit)} ：缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样</li>
 * <li> {@link CacheBuilder#expireAfterWrite(long, TimeUnit)} ：缓存项在给定时间内没有被写访问（创建或覆盖），则回收</li>
 * <li> {@link CacheBuilder#weakKeys()} ：使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收</li>
 * <li> {@link CacheBuilder#weakValues()} ：使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收</li>
 * <li> {@link CacheBuilder#softValues()} ：使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收</li>
 * </ul>
 * 
 * <p>
 * 其他策略：
 * <ul>
 * <li> {@link CacheBuilder#removalListener(RemovalListener)} ：缓存项移除监听，可以在监听器中做一些额外操作</li>
 * <li> {@link CacheBuilder#recordStats()} ：开启统计功能。统计打开后，<code>Cache.stats()</code> 方法会返回统计信息</li>
 * <li> {@link CacheBuilder#initialCapacity(int)} ：缓存初始容量</li>
 * <li> {@link CacheBuilder#concurrencyLevel(int)} ：设置并发数，即同一时间执行写入操作的最大线程数</li>
 * <li> {@link CacheBuilder#weigher(Weigher)} ：指定一个权重，并且可以通过 <code>CacheBuilder.maximumWeight(long)</code> 设置最大权重值</li>
 * </ul>
 * 
 * <p>
 * 显式清除: 任何时候，都可以显式地清除缓存项，而不是等到它被回收
 * <ul>
 * <li> {@link LoadingCache#invalidate(Object)} : 个别清除</li>
 * <li> {@link LoadingCache#invalidateAll(Iterable)} : 批量清除</li>
 * <li> {@link LoadingCache#invalidateAll()} : 清除所有</li>
 * </ul>
 * 
 * <p>
 * {@link LoadingCache#get(Object)} 与 {@link LoadingCache#getIfPresent(Object)} 的区别：
 * <p><code>get</code> 内部调用 <code>getOrLoad</code> 方法，缓存中有对应的值则返回，没有则调用 <code>CacheLoader.load</code> 方法
 * <p><code>getIfPresent</code> 缓存中有对应的值则返回，没有则返回NULL
 *
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class LoadingCacheTest {
    @Test
    public void whenCacheMiss_thenAutoLoad() {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);
        assertEquals(0, cache.size());
        assertEquals("HELLO", cache.getUnchecked("hello"));
        assertEquals(1, cache.size());
    }

    @Test
    public void whenCacheReachMaxSize_thenEviction() {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(3).build(loader);
        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("forth");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("first"));
        assertEquals("FORTH", cache.getIfPresent("forth"));
    }

    @Test
    public void whenCacheReachMaxWeight_thenEviction() {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        // 权重函数
        final Weigher<String, String> weighByLength = new Weigher<String, String>() {
            @Override
            public int weigh(final String key, final String value) {
                return value.length();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 最大总重
                .maximumWeight(16).weigher(weighByLength).build(loader);
        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("last");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("first"));
        assertEquals("LAST", cache.getIfPresent("last"));
    }

    @Test
    public void whenEntryIdle_thenEviction() throws InterruptedException {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 设置读缓存后n秒钟过期
                .expireAfterAccess(2, TimeUnit.MILLISECONDS).build(loader);
        cache.getUnchecked("hello");
        assertEquals(1, cache.size());
        cache.getUnchecked("hello");
        Thread.sleep(3);
        cache.getUnchecked("test");
        assertEquals(1, cache.size());
        assertNull(cache.getIfPresent("hello"));
    }

    @Test
    public void whenEntryLiveTimeExpire_thenEviction() throws InterruptedException {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 设置写缓存后n秒钟过期
                .expireAfterWrite(2, TimeUnit.MILLISECONDS).build(loader);
        cache.getUnchecked("hello");
        assertEquals(1, cache.size());
        Thread.sleep(3);
        cache.getUnchecked("test");
        assertEquals(1, cache.size());
        assertNull(cache.getIfPresent("hello"));
    }

    @Test
    @SuppressWarnings("unused")
    public void whenWeekKeyHasNoRef_thenRemoveFromCache() {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().weakKeys().build(loader);
    }

    @Test
    @SuppressWarnings("unused")
    public void whenSoftValue_thenRemoveFromCache() {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().softValues().build(loader);
    }

    @Test
    public void whenNullValue_thenOptional() {
        final CacheLoader<String, Optional<String>> loader = new CacheLoader<String, Optional<String>>() {
            @Override
            public final Optional<String> load(final String key) {
                return Optional.fromNullable(getSuffix(key));
            }
        };
        final LoadingCache<String, Optional<String>> cache = CacheBuilder.newBuilder().build(loader);
        assertEquals("txt", cache.getUnchecked("text.txt").get());
        assertFalse(cache.getUnchecked("hello").isPresent());
    }

    @Test
    public void whenLiveTimeEnd_thenRefresh() throws InterruptedException {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 只阻塞当前数据加载线程，其他线程返回旧值
                .expireAfterWrite(2, TimeUnit.SECONDS).build(loader);

        cache.getUnchecked("hello");
        assertNotNull(cache.getIfPresent("hello"));

        TimeUnit.SECONDS.sleep(2);
        assertNull(cache.getIfPresent("hello"));
    }

    @Test
    public void whenPreloadCache_thenUsePutAll() {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);
        final Map<String, String> map = new HashMap<String, String>();
        map.put("first", "FIRST");
        map.put("second", "SECOND");
        cache.putAll(map);
        assertEquals(2, cache.size());
    }

    @Test
    public void whenEntryRemovedFromCache_thenNotify() {
        final CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
        final RemovalListener<String, String> listener = new RemovalListener<String, String>() {
            @Override
            public void onRemoval(final RemovalNotification<String, String> n) {
                if (n.wasEvicted()) {
                    final String cause = n.getCause().name(); // 删除原因
                    assertEquals(RemovalCause.SIZE.toString(), cause);
                }
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(3).removalListener(listener)
                .build(loader);
        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("last");
        assertEquals(3, cache.size());
    }

    // UTIL

    private String getSuffix(final String str) {
        final int lastIndex = str.lastIndexOf('.');
        if (lastIndex == -1) {
            return null;
        }
        return str.substring(lastIndex + 1);
    }
}
