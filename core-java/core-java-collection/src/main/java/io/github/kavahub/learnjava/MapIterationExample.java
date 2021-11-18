package io.github.kavahub.learnjava;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapIterationExample {
    public static void main(String[] args) {
        MapIterationExample iteration = new MapIterationExample();
        Map<String, Integer> map = new HashMap<>();

        map.put("One", 1);
        map.put("Three", 3);
        map.put("Two", 2);

        System.out.println("Iterating Keys of Map Using KeySet");
        iteration.iterateKeys(map);

        System.out.println("Iterating Map Using Entry Set");
        iteration.iterateUsingEntrySet(map);

        System.out.println("Iterating Using Iterator and Map Entry");
        iteration.iterateUsingIteratorAndEntry(map);

        System.out.println("Iterating Using KeySet and For Each");
        iteration.iterateUsingKeySetAndForeach(map);

        System.out.println("Iterating Map Using Lambda Expression");
        iteration.iterateUsingLambda(map);

        System.out.println("Iterating Using Stream API");
        iteration.iterateUsingStreamAPI(map);
    }

    public void iterateUsingEntrySet(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public void iterateUsingLambda(Map<String, Integer> map) {
        map.forEach((k, v) -> System.out.println((k + ":" + v)));
    }

    public void iterateUsingIteratorAndEntry(Map<String, Integer> map) {
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet()
            .iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> pair = iterator.next();
            System.out.println(pair.getKey() + ":" + pair.getValue());
        }
    }

    public void iterateUsingKeySetAndForeach(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }

    public void iterateUsingStreamAPI(Map<String, Integer> map) {
        map.entrySet()
            .stream()
            .forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
    }

    public void iterateKeys(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key);
        }

    }
}
