package edu.netcracker.small_learning_things.leetcode.zigzag_conversion;

/**
 * @author svku0919
 * @version 10/07/2024-11:39
 */

public class Solution {
    public static String convert(String s, int numRows) {
        if (s.length() < 3) {
            return s;
        }
        if (numRows == 1) {
            return s;
        }
        final int gap = numRows * 2 - 2;
        StringBuilder sb = new StringBuilder();
        int index = 0;
        do {
            sb.append(s.charAt(index));
            index += gap;
        } while (index < s.length());

        for (int i = 1; i < numRows - 1; i++) {
            final int tempElem = gap - (i * 2);
            index = i;
            while (index < s.length()) {
                sb.append(s.charAt(index));
                if (index + tempElem >= s.length()) {
                    break;
                }
                sb.append(s.charAt(index + tempElem));
                index += gap;
            }
        }
        index = numRows - 1;
        while (index < s.length())
        {
            sb.append(s.charAt(index));
            index += gap;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(convert("ABC", 1));
    }
}
