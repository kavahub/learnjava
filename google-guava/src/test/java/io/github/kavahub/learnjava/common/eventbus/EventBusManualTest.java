package io.github.kavahub.learnjava.common.eventbus;

import com.google.common.eventbus.EventBus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EventBusManualTest {
    private static EventBus eventBus;

    @BeforeAll
    public static void setUp() {
        eventBus = new EventBus();
    }

    @Test
    public void givenRegisteChildListener_whenPostString_thenAllHandler() {
        ChildEventListener listener = new ChildEventListener();
        eventBus.register(listener);

        eventBus.post("event");

        eventBus.unregister(listener);

        // output:
        // fatherHandle:event
        // grænfɑHandle:event
        // childHandle:event
    }

    @Test
    public void givenRegisteChildListener_whenPostChild_thenAllHandler() {
        ChildEventListener listener = new ChildEventListener();
        eventBus.register(listener);

        eventBus.post(new ChildEvent("child event"));

        eventBus.unregister(listener);

        // output:
        // childHandle:ChildEvent[name=child event]
        // fatherHandle:ChildEvent[name=child event]
        // gandpaHandle:ChildEvent[name=child event]
        
    }

    @Test
    public void givenRegisteChildListener_whenPostFather_thenChildNotHandler() {
        ChildEventListener listener = new ChildEventListener();
        eventBus.register(listener);

        eventBus.post(new FatherEvent("father event"));

        eventBus.unregister(listener);

        // output:
        // fatherHandle:FatherEvent[name=father event]
        // gandpaHandle:FatherEvent[name=father event]    
    }

    @Test
    public void givenRegisteFatherListener_whenPostChild_thenFatherHandler() {
        FatherEventListener listener = new FatherEventListener();
        eventBus.register(listener);

        eventBus.post(new ChildEvent("child event"));

        eventBus.unregister(listener);

        // output:
        // fatherHandle:ChildEvent[name=child event]
        // gandpaHandle:ChildEvent[name=child event]
        
        
    }
}
