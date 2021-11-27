package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.jayway.awaitility.Awaitility.await;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link WeakHashMap} 示例
 * 
 * <p>
 * {@code WeakHashMap} 继承AbstractMap，实现了Map接口。和HashMap一样，WeakHashMap也是一个散列表，
 * 它存储的内容也是键值对(key-value)映射，而且键和值都可以是null
 * 
 * <p>
 * {@code WeakHashMap} 的键是虚引用，当垃圾回收器触发时，并不会阻止键被回收
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class WeakHashMapManualTest {
    @Test
    public void givenWeakHashMap_whenCacheValueThatHasNoReferenceToIt_GCShouldReclaimThatObject() {
        //given
        WeakHashMap<UniqueImageName, BigImage> map = new WeakHashMap<>();
        BigImage bigImage = new BigImage("image_id");
        UniqueImageName imageName = new UniqueImageName("name_of_big_image");

        map.put(imageName, bigImage);
        assertTrue(map.containsKey(imageName));

        //when big image key is not reference anywhere
        imageName = null;
        System.gc();

        //then GC will finally reclaim that object
        await().atMost(10, TimeUnit.SECONDS).until(map::isEmpty);
    }

    @Test
    public void givenWeakHashMap_whenCacheValueThatHasNoReferenceToIt_GCShouldReclaimThatObjectButLeaveReferencedObject() {
        //given
        WeakHashMap<UniqueImageName, BigImage> map = new WeakHashMap<>();
        BigImage bigImageFirst = new BigImage("foo");
        UniqueImageName imageNameFirst = new UniqueImageName("name_of_big_image");

        BigImage bigImageSecond = new BigImage("foo_2");
        UniqueImageName imageNameSecond = new UniqueImageName("name_of_big_image_2");

        map.put(imageNameFirst, bigImageFirst);
        map.put(imageNameSecond, bigImageSecond);
        assertTrue(map.containsKey(imageNameFirst));
        assertTrue(map.containsKey(imageNameSecond));

        //when
        imageNameFirst = null;
        System.gc();

        //then
        await().atMost(10, TimeUnit.SECONDS).until(() -> map.size() == 1);
        await().atMost(10, TimeUnit.SECONDS).until(() -> map.containsKey(imageNameSecond));
    }


    class BigImage {
        public final String imageId;

        BigImage(String imageId) {
            this.imageId = imageId;
        }
    }

    class UniqueImageName {
        public final String imageName;

        UniqueImageName(String imageName) {
            this.imageName = imageName;
        }
    }
}
