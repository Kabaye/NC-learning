package edu.netcracker.small_learning_things.leetcode.integer_to_roman;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author svku0919
 * @version 04/03/2024-15:22
 */

class Solution {
    static Map<Integer, String> symbols = Map.ofEntries(Map.entry(1, "I"),
            Map.entry(4, "IV"),
            Map.entry(5, "V"),
            Map.entry(9, "IX"),
            Map.entry(10, "X"),
            Map.entry(40, "XL"),
            Map.entry(50, "L"),
            Map.entry(90, "XC"),
            Map.entry(100, "C"),
            Map.entry(400, "CD"),
            Map.entry(500, "D"),
            Map.entry(900, "CM"),
            Map.entry(1000, "M"));
    static List<Integer> values = List.of(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1);

    public static String intToRoman(int num) {
        int tempNum = num;
        StringBuilder sb = new StringBuilder();

        Iterator<Integer> currentValIterator = values.iterator();
        while (tempNum > 0) {
            int currentVal = currentValIterator.next();
            String s = symbols.get(currentVal);
            while (tempNum - currentVal >= 0) {
                tempNum -= currentVal;
                sb.append(s);
            }
        }

        return sb.toString();
    }
}
