package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

import org.junit.jupiter.api.Test;

public class ClassToInstanceMapTest {
    @Test
    public void whenOfCalled_thenCreateEmptyImmutableMap() {
        // 创建了实例后，就不能put
        ClassToInstanceMap<Action> map = ImmutableClassToInstanceMap.of();
        assertTrue(map.isEmpty());
    }

    @Test
    public void whenCreateCalled_thenCreateEmptyMutableMap() {
        ClassToInstanceMap<Action> map = MutableClassToInstanceMap.create();
        assertTrue(map.isEmpty());
    }

    @Test
    public void whenOfWithParameterCalled_thenCreateSingleEntryMap() {
        ClassToInstanceMap<Action> map = ImmutableClassToInstanceMap.of(Save.class, new Save());
        assertEquals(1, map.size());
    }

    @Test
    public void whenBuilderUser_thenCreateMap() {
        ClassToInstanceMap<Action> map = ImmutableClassToInstanceMap.<Action>builder()
                .put(Save.class, new Save())
                .put(Open.class, new Open())
                .build();
        assertEquals(2, map.size());
    }

    @Test
    public void givenClassToInstanceMap_whenGetCalled_returnUpperBoundElement() {
        ClassToInstanceMap<Action> map = ImmutableClassToInstanceMap.of(Save.class, new Save());
        Action action = map.get(Save.class);
        assertTrue(action instanceof Save);

        // Use getInstance to avoid casting
        Save save = map.getInstance(Save.class);
        assertNotNull(save);
    }

    @Test
    public void givenClassToInstanceMap_whenPutCalled_returnPreviousElementUpperBound() {
        ClassToInstanceMap<Action> map = MutableClassToInstanceMap.create();
        map.put(Save.class, new Save());
        // Put again to get previous value returned
        Action action = map.put(Save.class, new Save());
        assertTrue(action instanceof Save);

        // Use putInstance to avoid casting
        Save save = map.putInstance(Save.class, new Save());
        assertNotNull(save);
    }

    abstract class Action {
    }
    
    class Save extends Action {
    }
    
    class Open extends Action {
    }
    
    class Delete extends Action {
    }
}
