package edu.netcracker.small_learning_things.leetcode.add_binary;

/**
 * @author svku0919
 * @version 19.06.2023-19:42
 */
public class Solution {
    public static String addBinary(String a, String b) {
        String leadingZeros = "0".repeat(Math.abs(a.length() - b.length()));

        if (a.length() < b.length()) {
            a = leadingZeros + a;
        } else {
            b = leadingZeros + b;
        }

        boolean addToNext = false;

        StringBuilder reversedResult = new StringBuilder();
        for (int i = a.length() - 1; i >= 0; i--) {
            if (a.charAt(i) == '1' && b.charAt(i) == '1') {
                if (addToNext) {
                    reversedResult.append("1");
                } else {
                    reversedResult.append("0");
                }
                addToNext = true;
            } else if (a.charAt(i) == '1' && b.charAt(i) == '0' || a.charAt(i) == '0' && b.charAt(i) == '1') {
                if (addToNext) {
                    reversedResult.append("0");
                } else {
                    reversedResult.append("1");
                }
            } else {
                if (addToNext) {
                    reversedResult.append("1");
                    addToNext = false;
                } else {
                    reversedResult.append("0");
                }
            }
        }
        if (addToNext) {
            reversedResult.append("1");
        }

        return reversedResult.reverse().toString();
    }

    public static void main(String[] args) {
        String s = addBinary("1111", "1111");
        System.out.println(s);
    }
}
