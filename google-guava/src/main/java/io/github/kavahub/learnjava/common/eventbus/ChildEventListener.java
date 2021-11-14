package io.github.kavahub.learnjava.common.eventbus;

import com.google.common.eventbus.Subscribe;
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
