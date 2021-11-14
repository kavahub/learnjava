package io.github.kavahub.learnjava.common.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomEventListener {
    private int eventsHandled;

    @Subscribe
    public void stringEvent(String event) {
        log.info("do event [" + event + "]");
        eventsHandled++;
    }

    @Subscribe
    public void someCustomEvent(CustomEvent customEvent) {
        log.info("do event [" + customEvent.getAction() + "]");
        eventsHandled++;
    }

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        log.info("unhandled event [" + deadEvent.getEvent() + "]");
        eventsHandled++;
    }

    @Subscribe
    public void handleException(Double event) {
        throw new RuntimeException(String.valueOf(event));
    }

    public int getEventsHandled() {
        return eventsHandled;
    }

    public void resetEventsHandled() {
        eventsHandled = 0;
    }
}
