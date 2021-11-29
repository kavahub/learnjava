package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Stream} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class StreamFeaturesTest {
    @Nested
    public class TakeAndDropWhileTest {

        public Stream<String> getStreamAfterTakeWhileOperation() {
            return Stream.iterate("", s -> s + "s").takeWhile(s -> s.length() < 10);
        }

        public Stream<String> getStreamAfterDropWhileOperation() {
            return Stream.iterate("", s -> s + "s").takeWhile(s -> s.length() < 10).dropWhile(s -> !s.contains("sssss"));
        }

        @Test
        public void testTakeWhileOperation() {
            List<String> list = getStreamAfterTakeWhileOperation().collect(Collectors.toList());

            assertEquals(10, list.size());

            assertEquals("", list.get(0));
            assertEquals("ss", list.get(2));
            assertEquals("sssssssss", list.get(list.size() - 1));
        }

        @Test
        public void testDropWhileOperation() {
            List<String> list = getStreamAfterDropWhileOperation().collect(Collectors.toList());

            assertEquals(5, list.size());

            assertEquals("sssss", list.get(0));
            assertEquals("sssssss", list.get(2));
            assertEquals("sssssssss", list.get(list.size() - 1));
        }

    }

    @Nested
    public class IterateTest {

        private Stream<Integer> getStream() {
            return Stream.iterate(0, i -> i < 10, i -> i + 1);
        }

        @Test
        public void testIterateOperation() {
            List<Integer> list = getStream().collect(Collectors.toList());

            assertEquals(10, list.size());

            assertEquals(Integer.valueOf(0), list.get(0));
            assertEquals(Integer.valueOf(5), list.get(5));
            assertEquals(Integer.valueOf(9), list.get(list.size() - 1));
        }

    }

    @Nested
    public class OfNullableTest {

        private List<String> collection = Arrays.asList("A", "B", "C");
        private Map<String, Integer> map = new HashMap<>() {
            {
                put("A", 10);
                put("C", 30);
            }
        };

        private Stream<Integer> getStreamWithOfNullable() {
            return collection.stream().flatMap(s -> Stream.ofNullable(map.get(s)));
        }

        private Stream<Integer> getStream() {
            return collection.stream().flatMap(s -> {
                Integer temp = map.get(s);
                return temp != null ? Stream.of(temp) : Stream.empty();
            });
        }

        private List<Integer> testOfNullableFrom(Stream<Integer> stream) {
            List<Integer> list = stream.collect(Collectors.toList());

            assertEquals(2, list.size());

            assertEquals(Integer.valueOf(10), list.get(0));
            assertEquals(Integer.valueOf(30), list.get(list.size() - 1));

            return list;
        }

        @Test
        public void testOfNullable() {

            assertEquals(testOfNullableFrom(getStream()), testOfNullableFrom(getStreamWithOfNullable()));

        }

    }  

    @Nested
    public class FilterTest {
        private List<Employee> employeeList = new ArrayList<Employee>();

        private List<Department> departmentList = new ArrayList<Department>();

        private void populate(List<Employee> EmplList, List<Department> deptList) {
            Employee employee1 = new Employee(1001, "empl1");
            Employee employee2 = new Employee(1002, "empl2", 1002);
            Employee employee3 = new Employee(1003, "empl3", 1003);
    
            Collections.addAll(EmplList, employee1, employee2, employee3);
    
            Department department1 = new Department(1002, "sales");
            Department department2 = new Department(1003, "marketing");
            Department department3 = new Department(1004, "sales");
    
            Collections.addAll(deptList, department1, department2, department3);
        }

        @Test
        public void givenDepartmentList_thenEmployeeListIsFilteredCorrectly() {
            Integer expectedId = 1002;
    
            populate(employeeList, departmentList);
    
            List<Employee> filteredList = employeeList.stream()
                .filter(empl -> departmentList.stream()
                    .anyMatch(dept -> dept.getDepartment()
                        .equals("sales") && dept.getId()
                        .equals(empl.getDepartmentId())))
                .collect(Collectors.toList());
    
            assertEquals(expectedId, filteredList.get(0)
                .getId());
        }
    }
}
