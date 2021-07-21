package edu.netcracker.small_learning_things.hash_map;

import java.util.HashMap;

/**
 * @author svku0919
 * @version 31.03.2021
 */
public class TestHashMap {
    public static void main(String[] args) {
        HashMap<Key, Integer> map = new HashMap<>();
        map.put(new Key("vishal"), 20);
        map.put(new Key("sachin"), 30);
        map.put(new Key("vaibhav"), 40);
        System.out.println(map.get(new Key("vaibhav")));
    }
}
