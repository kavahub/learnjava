package io.github.kavahub.learnjava.kafka.producer;

import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class EvenOddPartitioner extends DefaultPartitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        if (((String) key).length() % 2 == 0) {
            return 0;
        }

        return 1;
    }
    
}
