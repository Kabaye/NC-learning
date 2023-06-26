package edu.netcracker.small_learning_things.leetcode.length_of_last_word;

/**
 * @author svku0919
 * @version 26.06.2023-12:53
 */

public class Solution {
    public static int lengthOfLastWord(String s) {
        int currPos = s.length() - 1;
        while (currPos >= 0 && s.charAt(currPos) == ' ') {
            currPos--;
        }
        int trailingSpacesCount = s.length() - currPos - 1;
        while (currPos >= 0 && s.charAt(currPos) != ' ') {
            currPos--;
        }

        return s.length() - currPos - 1 - trailingSpacesCount;
    }

    public static void main(String[] args) {
        int i = lengthOfLastWord("Hello World");
        System.out.println(i);
    }
}
