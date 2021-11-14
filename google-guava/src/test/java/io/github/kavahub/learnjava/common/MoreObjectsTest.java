package io.github.kavahub.learnjava.common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.google.common.base.MoreObjects;

import org.junit.jupiter.api.Test;

import lombok.Getter;

public class MoreObjectsTest {
    @Test
    public void whenToString_shouldIncludeAllFields() throws Exception {
        User user = new User(12L, "John Doe", 25);

        assertThat(user.toString(), equalTo("User{id=12, name=John Doe, age=25}"));
    }

    @Test
    public void whenPlayerToString_shouldCallParentToString() throws Exception {
        User user = new Player(12L, "John Doe", 25);

        assertThat(user.toString(), equalTo("User{id=12, name=John Doe, age=25}"));
    }

    @Test
    public void whenAdministratorToString_shouldExecuteAdministratorToString() throws Exception {
        User user = new Administrator(12L, "John Doe", 25);

        assertThat(user.toString(), equalTo("Administrator{id=12, name=John Doe, age=25}"));
    }

    @Getter
    public class User{
        private long id;
        private String name;
        private int age;
    
        public User(long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
        
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(User.class)
                    .add("id", id)
                    .add("name", name)
                    .add("age", age)
                    .toString();
        }
    }

    public class Player extends User{
        public Player(long id, String name, int age) {
            super(id, name, age);
        }

    }

    public class Administrator extends User{
        public Administrator(long id, String name, int age) {
            super(id, name, age);
        }
    
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", getId())
                    .add("name", getName())
                    .add("age", getAge())
                    .toString();
        }
    }
}
