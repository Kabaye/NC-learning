package edu.netcracker.small_learning_things.longest_substring;

import java.util.HashMap;

/**
 * @author svku0919
 * @version 13.01.2023-15:50
 */

public class Solution {
    public static int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) {
            return 0;
        } else if (s.length() == 1) {
            return 1;
        }

        HashMap<Character, Integer> charToIndex = new HashMap<>();

        int maxSize = 1;
        int startIndex = 0;

        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length()) {
                maxSize = Math.max(maxSize, i - startIndex);
            } else {
                if (charToIndex.containsKey(s.charAt(i))) {
                    maxSize = Math.max(maxSize, i - startIndex);
                    startIndex = Math.max(startIndex, charToIndex.get(s.charAt(i))+1);
                }
                charToIndex.put(s.charAt(i), i);
            }
        }
        return maxSize;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abba"));
    }
}
