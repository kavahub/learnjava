package io.github.kavahub.learnjava.common.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * 
 * 子事件监听
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ChildEventListener extends FatherEventListener {
    @Subscribe
    public void childHandle(String event) {
        System.out.println("childHandle:" + event);
    }

    @Subscribe
    public void childHandle(ChildEvent event) {
        System.out.println("childHandle:" + event);
    }
}
