package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import org.junit.jupiter.api.Test;

import lombok.Getter;

/**
 * 
 * {@link FluentIterable} 主要用于过滤、转换集合中的数据；FluentIterable是一个抽象类，
 * 实现了Iterable接口，大多数方法都返回FluentIterable对象
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FluentIterableTest {
    private static final int ADULT_AGE = 18;

    @Test
    public void whenFilteringByAge_shouldFilterOnlyAdultUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", 45));
        users.add(new User(2L, "Michael", 27));
        users.add(new User(3L, "Max", 16));
        users.add(new User(4L, "Bob", 10));
        users.add(new User(5L, "Bill", 65));

        Predicate<User> byAge = input -> input.getAge() > ADULT_AGE;

        List<String> results = FluentIterable.from(users)
                // 过滤
                .filter(byAge)
                // 转换
                .transform(Functions.toStringFunction())
                // 收集
                .toList();

        assertThat(results.size(), equalTo(3));
    }

    @Test
    public void whenCreatingFluentIterableFromArray_shouldContainAllUsers() throws Exception {
        User[] usersArray = { new User(1L, "John", 45), new User(2L, "Max", 15) };
        FluentIterable<User> users = FluentIterable.from(usersArray);

        assertThat(users.size(), equalTo(2));
    }

    @Test
    public void whenAppendingElementsToFluentIterable_shouldContainAllUsers() throws Exception {
        User[] usersArray = { new User(1L, "John", 45), new User(2L, "Max", 15) };

        FluentIterable<User> users = FluentIterable.from(usersArray).append(new User(3L, "Bob", 23),
                new User(4L, "Bill", 17));

        assertThat(users.size(), equalTo(4));
    }

    @Test
    public void whenAppendingListToFluentIterable_shouldContainAllUsers() throws Exception {
        User[] usersArray = { new User(1L, "John", 45), new User(2L, "Max", 15) };

        List<User> usersList = new ArrayList<>();
        usersList.add(new User(3L, "David", 32));

        FluentIterable<User> users = FluentIterable.from(usersArray).append(usersList);

        assertThat(users.size(), equalTo(3));
    }

    @Test
    public void whenJoiningFluentIterableElements_shouldOutputAllUsers() throws Exception {
        User[] usersArray = { new User(1L, "John", 45), new User(2L, "Max", 15) };

        FluentIterable<User> users = FluentIterable.from(usersArray);

        assertThat(users.join(Joiner.on("; ")), equalTo("User{id=1, name=John, age=45}; User{id=2, name=Max, age=15}"));
    }

    @Getter
    public class User {
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
            return MoreObjects.toStringHelper(User.class).add("id", id).add("name", name).add("age", age).toString();
        }
    }
}
