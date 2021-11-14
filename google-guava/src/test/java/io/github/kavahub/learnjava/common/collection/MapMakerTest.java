package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;

import org.junit.jupiter.api.Test;

import lombok.Getter;

public class MapMakerTest {
    @Test
    public void whenCreateCaches_thenCreated() {
        ConcurrentMap<User, Session> sessionCache = new MapMaker().makeMap();
        assertNotNull(sessionCache);

        ConcurrentMap<User, Profile> profileCache = new MapMaker().makeMap();
        assertNotNull(profileCache);

        User userA = new User(1, "UserA");

        sessionCache.put(userA, new Session(100));
        assertThat(sessionCache.size(), equalTo(1));

        profileCache.put(userA, new Profile(1000, "Personal"));
        assertThat(profileCache.size(), equalTo(1));
    }

    @Test
    public void whenCreateCacheWithInitialCapacity_thenCreated() {
        ConcurrentMap<User, Profile> profileCache = new MapMaker().initialCapacity(100).makeMap();
        assertNotNull(profileCache);
    }

    @Test
    public void whenCreateCacheWithConcurrencyLevel_thenCreated() {
        // 并发数量
        ConcurrentMap<User, Session> sessionCache = new MapMaker().concurrencyLevel(10).makeMap();
        assertNotNull(sessionCache);
    }

    @Test
    public void whenCreateCacheWithWeakKeys_thenCreated() {
        ConcurrentMap<User, Session> sessionCache = new MapMaker().weakKeys().makeMap();
        assertNotNull(sessionCache);
    }

    @Test
    public void whenCreateCacheWithWeakValues_thenCreated() {
        ConcurrentMap<User, Profile> profileCache = new MapMaker().weakValues().makeMap();
        assertNotNull(profileCache);
    }

    @Getter
    public class Session {
        private long id;
    
        public Session(long id) {
            this.id = id;
        }
    }

    @Getter
    public class User {
        private long id;
        private String name;
    
        public User(long id, String name) {
            this.id = id;
            this.name = name;
        }    
    }

    @Getter
    public class Profile {
        private long id;
        private String type;
    
        public Profile(long id, String type) {
            this.id = id;
            this.type = type;
        }   
    }
    
}
