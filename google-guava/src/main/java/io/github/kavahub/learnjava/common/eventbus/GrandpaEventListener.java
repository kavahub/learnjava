package io.github.kavahub.learnjava.common.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * 
 * 祖事件监听
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public abstract class GrandpaEventListener {
    @Subscribe
    public void gandpaHandle(String event) {
        System.out.println("gandpaHandle:" + event);
    }

    @Subscribe
    public void gandpaHandle(GrandpaEvent event) {
        System.out.println("gandpaHandle:" + event);
    }
}
