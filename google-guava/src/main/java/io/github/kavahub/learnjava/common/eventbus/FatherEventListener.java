package io.github.kavahub.learnjava.common.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * 
 * 父事件监听
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FatherEventListener extends GrandpaEventListener {
    @Subscribe
    public void fatherHandle(String event) {
        System.out.println("fatherHandle:" + event);
    }

    @Subscribe
    public void fatherHandle(FatherEvent event) {
        System.out.println("fatherHandle:" + event);
    }
}
