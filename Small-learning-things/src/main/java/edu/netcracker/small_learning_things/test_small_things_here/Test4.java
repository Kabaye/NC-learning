package edu.netcracker.small_learning_things.test_small_things_here;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Test4 {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Map uncheckedMap = map;

        uncheckedMap.put(1, 111); // нет исключения при добавлении
        uncheckedMap.put("3", "three"); // нет исключения при добавлении

        System.out.println(map.get(1));
        try {
            Integer x = map.get("3"); // java.lang.ClassCastException. Исключение только при получении
            System.out.println(x);
        } catch (ClassCastException e) {
            System.out.println(e);
        }

        map = new HashMap<>();
        Map<String, Integer> checkedMap = Collections.checkedMap(map, String.class, Integer.class);
        uncheckedMap = checkedMap;

        try {
            uncheckedMap.put(1, 111); // java.lang.ClassCastException: Attempt to insert class java.lang.Integer key into map with key type class java.lang.String. Исключение уже при добавлении
        } catch (ClassCastException e) {
            System.out.println(e);
        }
        try {
            uncheckedMap.put("3", "three"); // java.lang.ClassCastException: Attempt to insert class java.lang.String value into map with value type class java.lang.Integer. Исключение уже при добавлении
        } catch (ClassCastException e) {
            System.out.println(e);
        }


        NavigableSet<Integer> set = new TreeSet<>();
        Set uncheckedSet = set;

        uncheckedSet.add("String"); // ok нет исключения при добавлении
        try {
            uncheckedSet.add(Double.valueOf(124D));
        } catch (Exception e) {
            System.out.println(e); // java.lang.ClassCastException: class java.lang.String cannot be cast to class java.lang.Double (потому что в данной коллекции объекты сравниваются друг с другом для построение сортированной коллекции)
        }

        try {
            set.contains(123); // исключение не при добавлении, а при получении
        } catch (Exception e) {
            System.out.println(e); // java.lang.ClassCastException: class java.lang.String cannot be cast to class java.lang.Integer
        }

        set = new TreeSet<>();
        NavigableSet<Integer> checkedNavigableSet = Collections.checkedNavigableSet(set, Integer.class);
        uncheckedSet = checkedNavigableSet;

        try {
            uncheckedSet.add(Double.valueOf(124D)); // исключение сразу при добавлении
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
