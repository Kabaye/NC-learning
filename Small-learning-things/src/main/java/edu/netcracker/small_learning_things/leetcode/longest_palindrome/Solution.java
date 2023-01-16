package edu.netcracker.small_learning_things.leetcode.longest_palindrome;

/**
 * @author svku0919
 * @version 13.01.2023-15:50
 */

public class Solution {
    public static String longestPalindrome(String s) {
        if (s.isEmpty()) {
            return "";
        } else if (s.length() == 1) {
            return s;
        }
        boolean[][] matrix = new boolean[s.length()][s.length()];

        int maxStart = 0;
        int maxEnd = 1;

        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (i == j) {
                    matrix[i][j] = true;
                } else if (j - i == 1) {
                    matrix[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    matrix[i][j] = matrix[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                }
                if (matrix[i][j] && j - i + 1 > maxEnd - maxStart) {
                    maxStart = i;
                    maxEnd = j + 1;
                }
            }
        }

        return s.substring(maxStart, maxEnd);
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("bb"));
    }
}
