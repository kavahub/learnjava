package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;

/**
 * 
 * {@link HashMap} 排序示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HashMapSorterExample {
    private static Map<String, Employee> map = new HashMap<>();

    public static void main(String[] args) {

        initialize();

        treeMapSortByKey();

        arrayListSortByValue();
        arrayListSortByKey();

        sortStream();

        sortGuava();

        addDuplicates();

        treeSetByKey();
        treeSetByValue();

    }

    private static void sortGuava() {
        System.out.println("sortGuava");
        final Ordering<String> naturalOrdering =
        Ordering.natural().onResultOf(Functions.forMap(map, null));

        System.out.println(ImmutableSortedMap.copyOf(map, naturalOrdering));
    }

    private static void sortStream() {
        System.out.println("sortStream");
        map.entrySet().stream()
                .sorted(Map.Entry.<String, Employee>comparingByKey().reversed())
                .forEach(System.out::println);

        Map<String, Employee> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        result.entrySet().forEach(System.out::println);
    }

    private static void treeSetByValue() {
        System.out.println("treeSetByValue");
        SortedSet<Employee> values = new TreeSet<>(map.values());
        System.out.println(values);
    }

    private static void treeSetByKey() {
        System.out.println("treeSetByKey");
        SortedSet<String> keysSet = new TreeSet<>(map.keySet());
        System.out.println(keysSet);
    }

    private static void treeMapSortByKey() {
        System.out.println("treeMapSortByKey");
        TreeMap<String, Employee> sorted = new TreeMap<>(map);
        sorted.putAll(map);

        sorted.entrySet().forEach(System.out::println);

    }

    private static void arrayListSortByValue() {
        System.out.println("arrayListSortByValue");
        List<Employee> employeeById = new ArrayList<>(map.values());

        Collections.sort(employeeById);

        System.out.println(employeeById);
    }

    private static void arrayListSortByKey() {
        System.out.println("arrayListSortByKey");
        List<String> employeeByKey = new ArrayList<>(map.keySet());
        Collections.sort(employeeByKey);
        System.out.println(employeeByKey);
    }

    private static void initialize() {
        Employee employee1 = new Employee(1, "Mher");
        map.put(employee1.getName(), employee1);
        Employee employee2 = new Employee(22, "Annie");
        map.put(employee2.getName(), employee2);
        Employee employee3 = new Employee(8, "John");
        map.put(employee3.getName(), employee3);
        Employee employee4 = new Employee(2, "George");
        map.put(employee4.getName(), employee4);
    }

    private static void addDuplicates() {
        Employee employee5 = new Employee(1, "Mher");
        map.put(employee5.getName(), employee5);
        Employee employee6 = new Employee(22, "Annie");
        map.put(employee6.getName(), employee6);
    }
}
