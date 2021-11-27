package io.github.kavahub.learnjava.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link HashFunction} hash函数，可以用于创建Hasher对象
 * 
 * <p>
 * {@link Hashing} 定义了一些hash函数
 * 
 * <p>
 * {@link Hasher} 计算hash值，提供了putXxx（）方法用于添加数据，以及hash()方法返回计算结果HashCode
 * 
 * <p>
 * {@link HashCode} hash值计算结果
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HashingTest {
    @Test
    public void whenHashingInSha384_hashFunctionShouldBeReturned() throws Exception {
        int inputData = 15;

        HashFunction hashFunction = Hashing.sha384();
        HashCode hashCode = hashFunction.hashInt(inputData);

        assertEquals("0904b6277381dcfbdddd6b6c66e4e3e8f83d4690718d8e6f272c891f24773a12feaf8c449fa6e42240a621b2b5e3cda8",
                hashCode.toString());
    }

    @Test
    public void whenConcatenatingHashFunction_concatenatedHashShouldBeReturned() throws Exception {
        int inputData = 15;

        HashFunction hashFunction = Hashing.concatenating(Hashing.crc32(), Hashing.crc32());
        HashFunction crc32Function = Hashing.crc32();

        HashCode hashCode = hashFunction.hashInt(inputData);
        HashCode crc32HashCode = crc32Function.hashInt(inputData);

        assertEquals(crc32HashCode.toString() + crc32HashCode.toString(), hashCode.toString());
    }
}
