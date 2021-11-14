package io.github.kavahub.learnjava.common.eventbus;

import com.google.common.eventbus.Subscribe;

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
