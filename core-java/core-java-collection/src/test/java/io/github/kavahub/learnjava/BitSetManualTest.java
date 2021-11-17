package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

/**
 * BitSet就是“位图”数据结构，根据“位图”的语义，数据的存在性可以使用bit位上的1或0来表示；一个bit具有2个值：0和1，正好可以用来表示false和true
 */
public class BitSetManualTest {

    @Test
    public void givenBoolArray_whenMemoryLayout_thenConsumeMoreThanOneBit() {
        boolean[] bits = new boolean[1024 * 1024];

        System.out.println(ClassLayout.parseInstance(bits).toPrintable());
    }

    @Test
    public void givenBitSet_whenMemoryLayout_thenConsumeOneBitPerFlag() {
        BitSet bitSet = new BitSet(1024 * 1024);

        System.out.println(GraphLayout.parseInstance(bitSet).toPrintable());
    }

    @Test
    public void givenBitSet_whenSetting_thenShouldBeTrue() {
        BitSet bitSet = new BitSet();

        bitSet.set(10);
        assertThat(bitSet.get(10)).isTrue();

        bitSet.set(20, 30);
        for (int i = 20; i <= 29; i++) {
            assertThat(bitSet.get(i)).isTrue();
        }
        assertThat(bitSet.get(30)).isFalse();

        bitSet.set(10, false);
        assertThat(bitSet.get(10)).isFalse();

        bitSet.set(20, 30, false);
        for (int i = 20; i <= 30; i++) {
            assertThat(bitSet.get(i)).isFalse();
        }
    }

    @Test
    public void givenBitSet_whenClearing_thenShouldBeFalse() {
        BitSet bitSet = new BitSet();
        bitSet.set(42);
        assertThat(bitSet.get(42)).isTrue();

        bitSet.clear(42);
        assertThat(bitSet.get(42)).isFalse();

        bitSet.set(10, 20);
        for (int i = 10; i < 20; i++) {
            assertThat(bitSet.get(i)).isTrue();
        }

        bitSet.clear(10, 20);
        for (int i = 10; i < 20; i++) {
            assertThat(bitSet.get(i)).isFalse();
        }

        bitSet.set(10, 20);
        bitSet.clear();
        for (int i = 0; i < 100; i++) {
            assertThat(bitSet.get(i)).isFalse();
        }
    }

    @Test
    public void givenBitSet_whenGettingElements_thenShouldReturnRequestedBits() {
        BitSet bitSet = new BitSet();
        bitSet.set(42);

        assertThat(bitSet.get(42)).isTrue();
        assertThat(bitSet.get(43)).isFalse();

        bitSet.set(10, 20);
        BitSet newBitSet = bitSet.get(10, 20);
        for (int i = 0; i < 10; i++) {
            assertThat(newBitSet.get(i)).isTrue();
        }
    }

    @Test
    public void givenBitSet_whenFlip_thenTogglesTrueToFalseAndViceVersa() {
        BitSet bitSet = new BitSet();
        bitSet.set(42);
        // 反转所有位，或者指定的位
        bitSet.flip(42);
        assertThat(bitSet.get(42)).isFalse();

        bitSet.flip(12);
        assertThat(bitSet.get(12)).isTrue();

        bitSet.flip(30, 40);
        for (int i = 30; i < 40; i++) {
            assertThat(bitSet.get(i)).isTrue();
        }
    }

    @Test
    public void givenBitSet_whenGettingTheSize_thenReturnsTheSize() {
        BitSet defaultBitSet = new BitSet();
        assertThat(defaultBitSet.size()).isEqualTo(64);

        BitSet bitSet = new BitSet(1024);
        assertThat(bitSet.size()).isEqualTo(1024);

        // 判断bitset中设置为1的数量
        assertThat(bitSet.cardinality()).isEqualTo(0);
        bitSet.set(10, 30);
        assertThat(bitSet.cardinality()).isEqualTo(30 - 10);

        assertThat(bitSet.length()).isEqualTo(30);
        bitSet.set(100);
        assertThat(bitSet.length()).isEqualTo(101);

        assertThat(bitSet.isEmpty()).isFalse();
        bitSet.clear();
        assertThat(bitSet.isEmpty()).isTrue();
    }

    @Test
    public void givenBitSet_whenSetOperations_thenShouldReturnAnotherBitSet() {
        BitSet first = new BitSet();
        first.set(5, 10);

        BitSet second = new BitSet();
        second.set(7, 15);

        // 指定的BitSet中有设置为true，那么此BitSet中的任何位被设置为true，方法返回true
        assertThat(first.intersects(second)).isTrue();

        // BitSet中的所有bit位做and运算
        first.and(second);
        assertThat(first.get(7)).isTrue();
        assertThat(first.get(8)).isTrue();
        assertThat(first.get(9)).isTrue();
        assertThat(first.get(10)).isFalse();

        first.clear();
        first.set(5, 10);

        first.xor(second);
        for (int i = 5; i < 7; i++) {
            assertThat(first.get(i)).isTrue();
        }
        for (int i = 10; i < 15; i++) {
            assertThat(first.get(i)).isTrue();
        }
    }

    @Test
    public void givenBitSet_whenStream_thenStreamsAllSetBits() {
        BitSet bitSet = new BitSet();
        bitSet.set(15, 25);

        bitSet.stream().forEach(System.out::println);
        assertThat(bitSet.stream().count()).isEqualTo(10);
    }

    @Test
    public void givenBitSet_whenNextOrPrev_thenReturnsTheNextOrPrevClearOrSetBit() {
        BitSet bitSet = new BitSet();
        bitSet.set(15, 25);

        assertThat(bitSet.nextSetBit(13)).isEqualTo(15);
        assertThat(bitSet.nextSetBit(25)).isEqualTo(-1);

        assertThat(bitSet.nextClearBit(23)).isEqualTo(25);

        assertThat(bitSet.previousClearBit(24)).isEqualTo(14);
        assertThat(bitSet.previousSetBit(29)).isEqualTo(24);
        assertThat(bitSet.previousSetBit(14)).isEqualTo(-1);
    }

    // 有1千万个随机数，随机数的范围在1到1亿之间。现在要求写出一种算法，将1到1亿之间没有在随机数找出来？
    @Test
    public void givenTenMillionRandomNumberInAHundredMillionRange_thenFindNumberOfNOTPresent() {
        long start = System.currentTimeMillis();
        // 一千万
        final int tenMillion = 10_000_000;
        final int aHundredMillion = tenMillion * 10;

        BitSet bitSet = new BitSet(aHundredMillion);
        for (int i = 0; i < tenMillion; i++) {
            int randomResult = ThreadLocalRandom.current().nextInt(aHundredMillion);
            bitSet.set(randomResult);
            // System.out.println(randomResult);
        }

        bitSet.flip(0, aHundredMillion);
        System.out.println("0~1亿不在上述随机数中有" + bitSet.cardinality());

        System.out.println("重复的随机数个数：" + (bitSet.cardinality() - aHundredMillion + tenMillion));
        // bitSet.stream().forEach(System.out::println);
        System.out.println("耗时（毫秒）" + (System.currentTimeMillis() - start));
    }

    /**
     * 使用BitSet排序大数值数组： 1千万个随机数排序
     */
    @Test
    public void givenLargeNumber_thenSort() {
        long start = System.currentTimeMillis();
        // 一千万
        final int tenMillion = 10_000_000;
        final int aHundredMillion = tenMillion * 10;

        BitSet bitSet = new BitSet(aHundredMillion);
        for (int i = 0; i < tenMillion; i++) {
            int randomResult = ThreadLocalRandom.current().nextInt(aHundredMillion);
            bitSet.set(randomResult);
        }

        //bitSet.stream().forEach(System.out::println);

        System.out.println("========= 前100 =========");
        int index = 0;
        int loop = 100;
        do {
            if(bitSet.get(index)) {
                System.out.print(index + ",");
                loop--;
            } 
            index++;
        }while(loop > 0);
        
        System.out.println("\n========= 后100 =========");
        index = aHundredMillion - 1;
        loop = 100;
        do {
            if(bitSet.get(index)) {
                System.out.print(index + ",");
                loop--;
            } 
            index--;
        }while(loop > 0);

        System.out.println("\n耗时（毫秒）" + (System.currentTimeMillis() - start));
    }
}
