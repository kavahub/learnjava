package io.github.kavahub.learnjava.enhance;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import com.google.common.base.Function;


/**
 * 
 * <code>Set</code> 集合转换成 {@link MapFromSet}。 规则是: <code>Set</code>集合数据做键值，定义
 * {@link Function} 函数将健转换成值
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MapFromSet<K, V> extends AbstractMap<K, V> {

    private class SingleEntry implements Entry<K, V> {
        private K key;

        public SingleEntry(K key) {
            this.key = key;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            V value = MapFromSet.this.cache.get(this.key);
            if (value == null) {
                value = MapFromSet.this.function.apply(this.key);
                MapFromSet.this.cache.put(this.key, value);
            }
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }

    private class MyEntrySet extends AbstractSet<Entry<K, V>> {

        public class EntryIterator implements Iterator<Entry<K, V>> {
            private Iterator<K> inner;

            public EntryIterator() {
                this.inner = MyEntrySet.this.keys.iterator();
            }

            @Override
            public boolean hasNext() {
                return this.inner.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                K key = this.inner.next();
                return new SingleEntry(key);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        private Set<K> keys;

        public MyEntrySet(Set<K> keys) {
            this.keys = keys;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public int size() {
            return this.keys.size();
        }

    }

    private WeakHashMap<K, V> cache;
    private Set<Entry<K, V>> entries;
    private Function<? super K, ? extends V> function;

    public MapFromSet(Set<K> keys, Function<? super K, ? extends V> function) {
        this.function = function;
        this.cache = new WeakHashMap<K, V>();
        this.entries = new MyEntrySet(keys);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.entries;
    }

}
