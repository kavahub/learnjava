package io.github.kavahub.learnjava.common.eventbus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.eventbus.EventBus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * 
 * 
 */
public class EventBusTest {
    private CustomEventListener listener;
    private EventBus eventBus;

    @BeforeEach
    public void setUp() {
        eventBus = new EventBus(
            // 异常处理
            (exception, content) -> {
                System.out.println("getEvent:" + content.getEvent());
                System.out.println("getSubscriber:" + content.getSubscriber());
                System.out.println("getEventBus:" + content.getEventBus());
                System.out.println("getSubscriberMethod:" + content.getSubscriberMethod());
                //exception.printStackTrace();
            }
        );
        listener = new CustomEventListener();

        eventBus.register(listener);
    }

    @AfterEach
    public void tearDown() {
        eventBus.unregister(listener);
    }

    @Test
    public void givenStringEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        eventBus.post("String Event");
        assertEquals(1, listener.getEventsHandled());
    }

    @Test
    public void givenCustomEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        CustomEvent customEvent = new CustomEvent("Custom Event");
        eventBus.post(customEvent);

        assertEquals(1, listener.getEventsHandled());
    }

    @Test
    public void givenUnSubscribadEvent_whenEventHandledByDeadEvent_thenSuccess() {
        listener.resetEventsHandled();

        eventBus.post(12345);
        assertEquals(1, listener.getEventsHandled());
    }

    @Test
    public void giveEventbus_whenException_thenLog() {
        eventBus.post(Double.valueOf("123"));
    }

    
}
