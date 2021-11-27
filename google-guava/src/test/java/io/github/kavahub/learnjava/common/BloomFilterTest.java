package io.github.kavahub.learnjava.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import org.junit.jupiter.api.Test;

/**
 * {@link BloomFilter} 布隆过滤器, 可以理解为是一个m位的数组，它有k个相互独立的哈希函数
 * 
 * <p>
 * {@link Funnel} 是Guava中定义的一个接口，定义了怎样将一个Object对象分解为primitive 类型，Hasher的putObject方法需要传入这样的对象。
 * 
 * <p>
 * 应用场景：
 * <ul>
 * <li> 检查垃圾邮箱的地址</li>
 * <li> 懒加载, 优化背景：查询订单需要关联预警订单数据，由于每查询一笔预警就要查询一次预警表，效率低，即是判断该订单是否预警, 可以先将预警的订单放到布隆过滤器中存放一份，则查询订单的时候可以用于关联. 应用该场景的原因：大部分订单还是正常的，所以没不要每次去关联 先去布隆过滤器查询该订单是否存在，不存在则直接返回正常，存在则去预警表查询，允许一定的误差率</li>
 * <li> Google著名的分布式数据库Bigtable以及Hbase使用了布隆过滤器来查找不存在的行或列，以及减少磁盘查找的IO次数</li>
 * <li> 文档存储检查系统也采用布隆过滤器来检测先前存储的数据</li>
 * <li> Goole Chrome浏览器使用了布隆过滤器加速安全浏览服务 垃圾邮件地址过滤 </li>
 * <li> 爬虫URL地址去重</li>
 * <li> 解决缓存穿透问题</li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class BloomFilterTest {
    @Test
    public void givenBloomFilter_whenAddNStringsToIt_thenShouldNotReturnAnyFalsePositive() {
        // when
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 500, 0.01);

        // when
        filter.put(1);
        filter.put(2);
        filter.put(3);

        // then
        // the probability that it returns true, but is actually false is 1%
        assertThat(filter.mightContain(1)).isTrue();
        assertThat(filter.mightContain(2)).isTrue();
        assertThat(filter.mightContain(3)).isTrue();

        assertThat(filter.mightContain(100)).isFalse();
    }

    @Test
    public void givenBloomFilter_whenAddNStringsToItMoreThanDefinedExpectedInsertions_thenItWillReturnTrueForAlmostAllElements() {
        // when
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 5, 0.01);

        // when
        IntStream.range(0, 100_000).forEach(filter::put);

        // then
        assertThat(filter.mightContain(1)).isTrue();
        assertThat(filter.mightContain(2)).isTrue();
        assertThat(filter.mightContain(3)).isTrue();
        assertThat(filter.mightContain(100_000_000)).isTrue();
    }

    @Test
    public void givenMillionString_whenMightContain_thenCountErrorRate() {
        final int insertions = 1000000;
        // 初始化一个存储string数据的布隆过滤器,默认fpp（误差率） 0.03
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), insertions);

        Set<String> set = new HashSet<String>(insertions);
        List<String> list = new ArrayList<String>(insertions);

        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            set.add(uuid);
            list.add(uuid);
        }

        int wrong = 0; // 布隆过滤器误判的次数
        int right = 0;// 布隆过滤器正确次数

        for (int i = 0; i < 10000; i++) {
            String str = i % 100 == 0 ? list.get(i / 100) : UUID.randomUUID().toString();
            if (bf.mightContain(str)) {
                if (set.contains(str)) {
                    right++;
                } else {
                    wrong++;
                }
            }
        }

        // right 为100
        System.out.println("right:" + right);
        // 因为误差率为3%，所以一万条数据wrong的值在300左右
        System.out.println("wrong:" + wrong);
    }
}
