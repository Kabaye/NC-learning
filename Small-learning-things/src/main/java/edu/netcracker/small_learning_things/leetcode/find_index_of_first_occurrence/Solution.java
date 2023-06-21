package edu.netcracker.small_learning_things.leetcode.find_index_of_first_occurrence;

/**
 * @author svku0919
 * @version 21.06.2023-15:24
 */

class Solution {
    public static int strStr(String haystack, String needle) {
        int needleIndex = 0;
        char[] haystackChars = haystack.toCharArray();
        char[] needleChars = needle.toCharArray();
        for (int i = 0; i < haystackChars.length; i++) {
            if (haystackChars[i] == needleChars[needleIndex]) {
                needleIndex++;
            } else {
                i = i - needleIndex;
                needleIndex = 0;
            }
            if (needleIndex == needleChars.length) {
                return i - needleIndex + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int i = strStr("mississippi", "issip");
        System.out.println(i);
    }
}
