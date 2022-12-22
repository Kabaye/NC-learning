package edu.netcracker.small_learning_things.compute_if_present;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author svku0919
 * @version 23.11.2022-14:53
 */

public class ComputeIfPresentMapTest {
    public static void main(String[] args) {
        Map<String, Integer> test = new ConcurrentHashMap<>();
        test.put("1",1);
        test.put("2",2);
        test.put("3",3);
        test.put("4",5);
        test.put("5",5);
        test.put("6",6);

        System.out.println(test.computeIfPresent("4", (s, integer) -> null));
        System.out.println(test.computeIfPresent("5", (s, integer) -> null));
        System.out.println(test.computeIfPresent("6", (s, integer) -> null));

        System.out.println(test);
    }
}
